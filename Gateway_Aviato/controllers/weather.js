var request = require('request');
var uuid = require('node-uuid');

exports.getWeather = (req, res) => {
  res.render('weather', {
    title: 'Weather'
  });
};

exports.postWeather = (req, res, next) => {
  req.assert('station', 'Station cannot be blank').notEmpty();
  req.assert('date', 'Date cannot be blank').isDate();
  req.assert('time', 'Please fill in time in the correct format').notEmpty();
  
  const errors = req.validationErrors();
  if (errors) {
    req.flash('errors', errors);
    return res.redirect('/weather');
  }
  
  var time = req.body.time.split(':').join('').concat('00');
  
  var userRequest = {};
  userRequest.userName = req.user.email,
  userRequest.stationName = req.body.station;
  userRequest.date = req.body.date;
  userRequest.time = time;
  userRequest.requestId = uuid.v1();
  //console.log('The JSON is: ' + JSON.stringify(userRequest));

  var reqId = userRequest.requestId;

  // Data Ingestor - http://52.15.57.97:7000/zookeeper-app/webapi/ingestor/delegate
  // Storm Detector - http://52.15.57.97:7000/zookeeper-app/webapi/detector/delegate
  // Storm Clustering - http://52.15.57.97:7000/zookeeper-app/webapi/clustering/delegate                        
  // Trigger Forecast - http://52.15.57.97:7000/zookeeper-app/webapi/trigger/delegate
  // Run Forecast - http://52.15.57.97:7000/zookeeper-app/webapi/forecast/delegate

  var urlDataIngestor, urlStormDetector, urlStormClustering, urlTriggerForecast, urlRunForecast;
  request({
    url: 'http://52.15.57.97:9000/dataingestor/webapi/ingestor/delegate',
    method: 'GET',
  }, function(error, response, body){
    urlDataIngestor = body;
    console.log('Got back Data Ingestor URL: ' + urlDataIngestor);
  });
  request({
    url: 'http://52.15.57.97:7000/zookeeper-app/webapi/detector/delegate',
    method: 'GET',
  }, function(error, response, body){
    urlStormDetector = body;
    console.log('Got back Storm Det URL: ' + urlStormDetector);
  });
  request({
    url: 'http://52.15.57.97:7000/zookeeper-app/webapi/clustering/delegate',
    method: 'GET',
  }, function(error, response, body){
    urlStormClustering = body;
    console.log('Got back Storm CLustering URL: ' + urlStormClustering);
  });
  request({
    url: 'http://52.15.57.97:7000/zookeeper-app/webapi/trigger/delegate',
    method: 'GET',
  }, function(error, response, body){
    urlTriggerForecast = body;
    console.log('Got back Trigger Forecast URL: ' + urlTriggerForecast);
  });
  request({
    url: 'http://52.15.57.97:7000/zookeeper-app/webapi/forecast/delegate',
    method: 'GET',
  }, function(error, response, body){
    urlRunForecast = body;
    console.log('Got back Run Forecast URL: ' + urlRunForecast);
  });
  
  var requestSuccess = 0;
  request({ // Request to the Data Ingestor
    url: urlDataIngestor,
    method: 'POST',
    headers: { 'Content-Type': 'application/json'},
    json: userRequest
  }, function(error, response, body){
    if(error) {
    	console.log('Generic error while connecting to the data ingestor');
      console.log(error);
      requestSuccess = -1;
    }
    else if(response.statusCode != 200){
    	console.log('There was an error connecting to the Data Ingestor. Response: ' + response.statusCode);
      requestSuccess = 1;
    }
    else if (response.statusCode == 200){
    	console.log('Response from Data Ingestor is: ' + response.statusCode);
    	var diResponse = response.body;

      request({ // Request to the storm detector
        url: urlStormDetector,
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        json: diResponse
        },function(errorDet, responseDet, bodyDet){
          if(errorDet){
            console.log('Generic error while connecting to the storm detector.');
            console.log(errorDet);
            requestSuccess = -1;
          }
          else if(responseDet.statusCode != 200){
            console.log(responseDet.statusCode + ' while connecting to the storm detector. ReqId is: ' + reqId);
            requestSuccess = 2;
          }
          else if(responseDet.statusCode == 200){
            console.log('Storm detection was successful.');
            request({ // Request to the storm clustering
              url: urlStormClustering,
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              json: responseDet.body
              },function(errorClust, responseClust, bodyClust){
                if(errorClust){
                  console.log('Generic error while connecting to the storm clustering.');
                  console.log(errorClust);
                  requestSuccess = -1;
                }
                else if(responseClust.statusCode != 200){
                  console.log(responseClust.statusCode + ' while connecting to the storm clustering. ReqId is: ' + reqId);
                  requestSuccess = 2;
                }
                else if(responseClust.statusCode == 200){
                  request({ // Request to the forecast trigger
                    url: urlTriggerForecast,
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    json: responseDet.body
                    },function(errorTrig, responseTrig, bodyTrig){
                      if(errorTrig){
                        console.log('Generic error while connecting to the forecast trigger.');
                        console.log(errorTrig);
                        requestSuccess = -1;
                      }
                      else if(responseTrig.statusCode != 200){
                        console.log(responseTrig.statusCode + ' while connecting to the forecast trigger. ReqId is: ' + reqId);
                        requestSuccess = 2;
                      }
                      else if(responseTrig.statusCode == 200){
                        console.log('From forecast trigger: ' + bodyTrig.trigger_response);

                        if(bodyTrig.trigger_response == 'Yes'){
                            request({
                                url: urlRunForecast,
                                method: 'POST',
                                headers: { 'Content-Type': 'application/json' },
                                json: diResponse
                            }, function(errorRun, responseRun, bodyRun){
                                if(errorRun){
                                    console.log('Generic error while connecting to the forecaster.');
                                    console.log(errorRun);
                                    requestSuccess = -1;
                                }
                                else if(responseRun.statusCode != 200){
                                    console.log('Error code ' + responseRun.statusCode + ' while connecting to the run forecaster. ReqId is: ' + reqId);
                                    requestSuccess = 3;
                                }
                                else if(responseRun.statusCode == 200){
                                    //console.log('Runforecast was successful');    
                                }
                                if(requestSuccess == 0){
                                    //var outputMessage = body.temperature;
                                    req.flash('success', {msg: 'Storm detection was successful.'});
                                    req.session.forecastData = bodyRun;
                                    res.redirect('/results');
                                }
                                else{
                                    req.flash('error', {msg: 'Storm detection was not successful.'});
                                    res.redirect('/weather');   
                                }
                            });
                        }
                        else{
                            req.flash('success', {msg: 'No storm is predicted.'});
                            res.redirect('/weather');
                        }
                      }
                  });
                }
            });
          }
        });
      

    }
  });

  


  //req.flash('success', { msg: '' });
  //res.redirect('/weather');
};


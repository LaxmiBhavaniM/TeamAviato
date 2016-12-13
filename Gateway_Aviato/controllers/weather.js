var request = require('request');
var uuid = require('node-uuid');
var async = require('async');
var jobs = require('./jobs');

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
  
  var mesosReq = 'ingestor_job';
  var jobName = 'team_aviato_' + new Date().valueOf();
      
  var userRequest = {};
  userRequest.userName = req.user.email,
  userRequest.stationName = req.body.station;
  userRequest.date = req.body.date;
  userRequest.time = time;
  userRequest.requestId = uuid.v1();
  //console.log('The JSON is: ' + JSON.stringify(userRequest));

  var reqId = userRequest.requestId;

  var url_ingestor_delegator   = 'http://35.164.24.104:7000/zookeeper-app/webapi/ingestor/delegate';
  var url_detector_delegator   = 'http://35.164.24.104:7000/zookeeper-app/webapi/detector/delegate';
  var url_clustering_delegator = 'http://35.164.24.104:7000/zookeeper-app/webapi/clusteringDelegate/delegate';
  var url_trigger_delegator    = 'http://35.164.24.104:7000/zookeeper-app/webapi/trigger/delegate';
  var url_forecast_delegator   = 'http://35.164.24.104:7000/zookeeper-app/webapi/forecast/delegate';

  var urlDataIngestor, urlStormDetector, urlStormClustering, urlTriggerForecast, urlRunForecast;
  var diResponse, sdResponse, scResponse, ftResponse, rfResponse;

  async.auto({
    one: function(callback){
      console.log('Started Ingestor');
      request({
        url: url_ingestor_delegator,
        method: 'GET',
      }, function(error, response, body){
        if(error){ console.log(error); callback(error, 'Error while connecting to the Ingestor delegator.');}
        else{
          console.log('Ingestor: ' + response.statusCode);
          if(response.statusCode == 200){
            urlDataIngestor = response.body;
            callback(null, 'success');
          }
          else{
            callback(new Error(), 'Received non 200 response from the Ingestor Delegator.');
          }
        }
      });
    },
    two: function(callback){
      console.log('Started Detector');
      request({
        url: url_detector_delegator,
        method: 'GET',
      }, function(error, response, body){
        if(error){ console.log(error); callback(error, 'Error while connecting to the Detector Delegator.');}
        else{
          console.log('Detector: ' + response.statusCode);
          if(response.statusCode == 200){
            urlStormDetector = response.body;
            callback(null, 'success');
          }
          else{
            callback(new Error(), 'Received non 200 response from the Detector Delegator.');
          }
        }
      });
    },
    three: function(callback){
      console.log('Started Clustering');
      request({
        url: url_clustering_delegator,
        method: 'GET',
      }, function(error, response, body){
        if(error){ console.log(error); callback(error, 'Error while connecting to the Clustering Delegator.');}
        else{
          console.log('Clustering: ' + response.statusCode);
          if(response.statusCode == 200){
            urlStormClustering = response.body;
            callback(null, 'success');
          }
          else{
            callback(new Error(), 'Received non 200 response from the Clustering Delegator.');
          }
        }
      });
    },
    four: function(callback){
      console.log('Started Trigger');
      request({
        url: url_trigger_delegator,
        method: 'GET',
      }, function(error, response, body){
        if(error){ console.log(error); callback(error, 'Error while connecting to the Trigger Delegator.');}
        else{
          console.log('Trigger: ' + response.statusCode);
          if(response.statusCode == 200){
            urlTriggerForecast = response.body;
            callback(null, 'success');
          }
          else{
            callback(new Error(), 'Received non 200 response from the Trigger Delegator.');
          }
        }
      });
    },
    five: function(callback){
      console.log('Started Forecast');
      request({
        url: url_forecast_delegator,
        method: 'GET',
      }, function(error, response, body){
        if(error){ console.log(error); callback(error, 'Error while connecting to the Forecast Delegator.');}
        else{
          console.log('Forecast: ' + response.statusCode);
          if(response.statusCode == 200){
            urlRunForecast = response.body;
            callback(null, 'success');
          }
          else{
            callback(new Error(), 'Received non 200 response from the Forecast Delegator.');
          }
        }
      });
    },
    last: ['one', 'two', 'three', 'four', 'five', function(results, callback){
      console.log('In the last function');
      console.log(urlDataIngestor);
      console.log(urlStormDetector);
      console.log(urlStormClustering);
      console.log(urlTriggerForecast);
      console.log(urlRunForecast);
      callback(null, 'Success');  
    }],
    f0: ['last', function(results, callback){
      console.log('Connecting to Ingestor microservice - Mesos Endpoint');
      console.log('Giving job name: ' + jobName);
      request({ // Request to the Data Ingestor
        url: urlDataIngestor,
        method: 'POST',
        headers: { 'Content-Type': 'text/plain'},
        body: jobName
        },
        function(error, response, body){
          if(error) {
            console.log('Generic error while connecting to the data ingestor Mesos endpoint');
            console.log(error);
            requestSuccess = -1;
            callback(error, 'Error while connecting to the Ingestor Mesos Endpoint.');
          }
          else if(response.statusCode != 200){
            console.log('There was an error connecting to the Data Ingestor Mesos Endpoint. Response: ' + response.statusCode);
            requestSuccess = 1;
            callback(new Error(), 'Non 200 while connecting to the Ingestor Mesos Endpoint.');
          }
          else if (response.statusCode == 200){
            console.log('Response from Data Ingestor Mesos Endpoint is: ' + response.statusCode);
            diResponse = response.body;
            callback(null, 'Success');
          }
      });
    }],
    f1: ['f0', function(results, callback){
      console.log('Connecting to Ingestor microservice');
      request({ // Request to the Data Ingestor
        url: urlDataIngestor.slice(0, -11) + 'url',
        method: 'POST',
        headers: { 'Content-Type': 'application/json'},
        json: userRequest
        },
        function(error, response, body){
          if(error) {
            console.log('Generic error while connecting to the data ingestor');
            console.log(error);
            requestSuccess = -1;
            callback(error, 'Error while connecting to the Ingestor.');
          }
          else if(response.statusCode != 200){
            console.log('There was an error connecting to the Data Ingestor. Response: ' + response.statusCode);
            requestSuccess = 1;
            callback(new Error(), 'Non 200 while connecting to the Ingestor.');
          }
          else if (response.statusCode == 200){
            console.log('Response from Data Ingestor is: ' + response.statusCode);
            diResponse = response.body;
            console.log(diResponse);
            callback(null, 'Success');
          }
      });
    }],
    f2: ['f1', function(results, callback){
      request({ // Request to the storm detector
        url: urlStormDetector,
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        json: diResponse
        },
        function(error, response, body){
          if(error) {
            console.log('Generic error while connecting to the storm detector');
            console.log(error);
            requestSuccess = -1;
            callback(error, 'Error while connecting to the Detector.');
          }
          else if(response.statusCode != 200){
            console.log('There was an error connecting to the Detector. Response: ' + response.statusCode);
            requestSuccess = 1;
            callback(new Error(), 'Non 200 while connecting to the Detector.');
          }
          else if (response.statusCode == 200){
            console.log('Response from Detector is: ' + response.statusCode);
            sdResponse = response.body;
            callback(null, 'Success');
          }
      });
    }],
    f3: ['f2', function(results, callback){
      request({ // Request to the storm clustering
        url: urlStormClustering,
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        json: sdResponse
        },
        function(error, response, body){
          if(error) {
            console.log('Generic error while connecting to the storm clustering');
            console.log(error);
            requestSuccess = -1;
            callback(error, 'Error while connecting to the Clustering.');
          }
          else if(response.statusCode != 200){
            console.log('There was an error connecting to the Clustering. Response: ' + response.statusCode);
            requestSuccess = 1;
            callback(new Error(), 'Non 200 while connecting to the Clustering.');
          }
          else if (response.statusCode == 200){
            console.log('Response from Clustering is: ' + response.statusCode);
            scResponse = response.body;
            callback(null, 'Success');
          }
      });
    }],
    f4: ['f3', function(results, callback){
      request({ // Request to the forecast trigger
        url: urlTriggerForecast,
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        json: scResponse
        },
        function(error, response, body){
          if(error) {
            console.log('Generic error while connecting to the Forecast Trigger');
            console.log(error);
            requestSuccess = -1;
            callback(error, 'Error while connecting to the Forecast Trigger.');
          }
          else if(response.statusCode != 200){
            console.log('There was an error connecting to the Forecast Trigger. Response: ' + response.statusCode);
            requestSuccess = 1;
            callback(new Error(), 'Non 200 while connecting to the Forecast Trigger.');
          }
          else if (response.statusCode == 200){
            console.log('Response from Forecast Trigger is: ' + response.statusCode);
            ftResponse = response.body;
            console.log(ftResponse);//bodyTrig.trigger_response == 'Yes'
            callback(null, 'Success');
          }
      });
    }],
    f5: ['f4', function(results, callback){
      if(ftResponse.trigger_response == 'Yes'){
        console.log('Got back a yes from the forecast trigger.');
        request({ // Request to the storm clustering
          url: urlRunForecast,
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          json: diResponse
          },
          function(error, response, body){
            if(error) {
              console.log('Generic error while connecting to Run Forecast');
              console.log(error);
              requestSuccess = -1;
              callback(error, 'Error while connecting to Run Forecast.');
            }
            else if(response.statusCode != 200){
              console.log('There was an error connecting to Run Forecast. Response: ' + response.statusCode);
              requestSuccess = 1;
              callback(new Error(), 'Non 200 while connecting to Run Forecast.');
            }
            else if (response.statusCode == 200){
              console.log('Response from Run Forecast is: ' + response.statusCode);
              rfResponse = response.body;
              callback(null, 'Success');
            }
        });
      }
      else{
        callback(null, 'no');
      }
    }],
  }, function(err, results){
    console.log('In the final callback');
    console.log(results);
    
    if(err){
      console.log('Detected an error.');
      req.flash('info', {msg: 'There was an error in the process.'});
      res.redirect('/weather');
    }
    //else if(results['f5'] == 'no'){
    //  req.flash('success', {msg: 'No storm is predicted.'});
    //  res.redirect('/weather');  
    //}
    else{
      jobs.addJob(req.user.email, jobName);
      console.log('Just added into Mongo');
      req.flash('success', {msg: 'Job was successfully created.'});
      //req.session.forecastData = rfResponse;
      res.redirect('/weather');  
    }
    //res.redirect('/weather');
  });

  
  var requestSuccess = 0;
    


  //req.flash('success', { msg: '' });
  //res.redirect('/weather');
};


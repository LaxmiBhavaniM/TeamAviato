var request = require('request');
var EventEmitter = require("events").EventEmitter;
const Job = require('../models/Job');

exports.getJobs = (req, res) => { 
  // Using logic from: http://stackoverflow.com/a/30485963 to store data from request using emitter
 
  var results = new EventEmitter();
  var pageData = {};
  var myReqObject = {};
  myReqObject.userName = req.user.email;

  var listOfJobs = '[';   //[];//'["team_aviato_1481618117674"]';
  var arr = [];
  Job.find({ 'user': req.user.email }, function (err, docs) {
    // docs is an array
    var arr = [];
    if(docs.length <= 0){
      res.render('jobs', { layout : 'jobs', resultData: '{}' });
      return;
    }
    listOfJobs = listOfJobs + '"' + docs[0]['jobName'] + '"'
    for(i = 1; i < docs.length; i++){
      //console.log(userJobs[i]['jobName']);
      listOfJobs = listOfJobs + ', "' + docs[i]['jobName'] + '"';
      arr.push(docs[i]['jobName']);
    }
    listOfJobs = listOfJobs + ']';
    console.log('This is the array: ' + listOfJobs);
  
  request({
  	url: 'http://52.15.57.97:9000/dataingestor/webapi/service/getJobDetails',
  	method: 'POST',
  	headers: { 'Content-Type': 'application/json' },
  	body: listOfJobs
  }, function(error, response, body){
  	if(error){
  		console.log('Generic error while connecting to the registry.');
  		console.log(error);
  	}
  	else if(response.statusCode != 200){
  		console.log(response.statusCode + ' while getting jobs.');
  		//requestSuccess = 2;
  	}
  	else if(response.statusCode == 200){
  		console.log('Got from Mesos: ' + JSON.stringify(body));
  		results.data = body;
    	results.emit('update');
  	}
  });
  });
  results.on('update', function () {
    console.log('These are the jobs: ');
    console.log(results.data);
    pageData = results.data;
    /*res.render('jobs', { 
      title: 'Jobs',
      pageData: results.data 
    });*/

    var ob = { action:"date +%s", result:"1367263074"};
    res.render('jobs', { layout : 'jobs', resultData: pageData });
  });


};

exports.addJob = (userName, jobName) => {
  const job = new Job({
    user: userName,
    jobName: jobName
  });

  Job.create(job, function (err, small) {
    if (err) return handleError(err);
    else console.log('Saved successfully to Mongo');
    console.log(small);
    // saved!
  });
};

exports.submitJob = (req, res) =>{
  console.log('Resubmitting job: ' + req.params.name);
  var urlDataIngestor;
  var url_ingestor_delegator  = 'http://35.164.24.104:7000/zookeeper-app/webapi/ingestor/delegate';

  request({
    url: url_ingestor_delegator,
    method: 'GET',
  }, function(error, response, body){
    if(error){ console.log(error); callback(error, 'Error while connecting to the Ingestor delegator.');}
    else{
      console.log('Ingestor: ' + response.statusCode);
      if(response.statusCode == 200){
        urlDataIngestor = response.body;
        //callback(null, 'success');
      }
      else{
        //callback(new Error(), 'Received non 200 response from the Ingestor Delegator.');
      }
    }
    request({
      url: urlDataIngestor,
      method: 'POST',
      headers: { 'Content-Type': 'text/plain'},
      body: req.params.name + '_1'
      },
      function(error, response, body){
        if(error) {
          console.log('Generic error while connecting to the data ingestor Mesos endpoint');
          console.log(error);
          requestSuccess = -1;
          //callback(error, 'Error while connecting to the Ingestor Mesos Endpoint.');
        }
        else if(response.statusCode != 200){
          console.log('There was an error connecting to the Data Ingestor Mesos Endpoint. Response: ' + response.statusCode);
          requestSuccess = 1;
          //callback(new Error(), 'Non 200 while connecting to the Ingestor Mesos Endpoint.');
        }
        else if (response.statusCode == 200){
          console.log('Successfully resubmitted job: ' + response.statusCode);
          //diResponse = response.body;
          //callback(null, 'Success');
        }
        req.flash('success', {msg: 'Job was successfully resubmitted.'});
        res.redirect('/jobs');  
    });
  });
  
};

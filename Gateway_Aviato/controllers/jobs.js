var request = require('request');
var EventEmitter = require("events").EventEmitter;
const Job = require('../models/Job');

exports.getJobs = (req, res) => { 
  // Using logic from: http://stackoverflow.com/a/30485963 to store data from request using emitter
 
  var results = new EventEmitter();

  var myReqObject = {};
  myReqObject.userName = req.user.email;

  var listOfJobs = '[';   //[];//'["team_aviato_1481618117674"]';
  var arr = [];
  Job.find({ 'user': req.user.email }, function (err, docs) {
    // docs is an array
    var arr = [];
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
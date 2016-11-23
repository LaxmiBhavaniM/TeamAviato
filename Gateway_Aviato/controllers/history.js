var request = require('request');
var EventEmitter = require("events").EventEmitter;

exports.getHistory = (req, res) => { 
  // Using logic from: http://stackoverflow.com/a/30485963 to store data from request using emitter
 
  var results = new EventEmitter();

  var myReqObject = {};
  myReqObject.userName = req.user.email;

  request({
  	url: 'http://ec2-35-164-24-104.us-west-2.compute.amazonaws.com:8082/registry/v1/service/user',
  	method: 'POST',
  	headers: { 'Content-Type': 'application/json' },
  	json: myReqObject
  }, function(error, response, body){
  	if(error){
  		console.log('Generic error while connecting to the registry.');
  		console.log(error);
  	}
  	else if(response.statusCode != 200){
  		console.log(response.statusCode + ' while connecting to the storm detector. ReqId is: ' + reqId);
  		requestSuccess = 2;
  	}
  	else if(response.statusCode == 200){
  		console.log(body);
  		results.data = body;
    	results.emit('update');
  	}
  });

  results.on('update', function () {
    console.log('This is from the registry: ');
    console.log(results.data[0]);
    res.render('history', { 
      title: 'History',
      pageData: results.data 
    }); 
  });


};

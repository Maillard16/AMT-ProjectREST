var http = require("http"); 
var https = require("https"); 
//var sleep = require('sleep'); 
 
 
 
var port = 80; 
var hostname = '127.0.0.1'; 
var pathGetSensors = '/api/sensors'; 
var pathPostValues = '/api/sensors'; 
 
 
/** 
* getJSON: REST get request returning JSON object(s) 
* @param options: http options object 
* @param callback: callback to pass the results JSON object(s) back 
*/ 
var sendRequest = function(options, onResult, post_data) 
{ 
    console.log("Start"); 
 
    var prot = options.port == 443 ? https : http; 
    var req = prot.request(options, function(res) 
    { 
        var output = ''; 
        console.log(options.host + ':' + res.statusCode); 
        res.setEncoding('utf8'); 
 
        res.on('data', function (chunk) { 
            output += chunk; 
        }); 
 
        res.on('end', function() { 
            var obj = JSON.parse(output); 
            //var obj = output; 
            onResult(res.statusCode, obj); 
        }); 
    }); 
 
    req.on('error', function(err) { 
        //res.send('error: ' + err.message); 
    }); 
 
    req.write(post_data); 
    req.end(); 
}; 
 
var sendValues = function(ids) 
{     
    var loopfun = function() 
    { 
        for(var i = 0; i < ids.length; i++) 
        { 
            console.log("Id:"+ids[i]._id); 
            var post_data = [
			{id: 1,
			name:'test',
			description:'description',
			type:'type',
			visibility:true,
			organisation:{
			id:1,
			name:'orgtest'
			contact:'jeanclaud'}
			}]; 
            console.log(post_data); 
             
            var options = { 
                host: hostname, 
                port: port, 
                path: pathPostValues, 
                method: 'POST', 
                headers: { 
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8', 
                    'Content-length': post_data.length 
                } 
            }; 
            sendRequest(options, function(statusCode, result){}, post_data); 
        } 
    }; 
     
    myWaitLoop(10, 10, loopfun); 
     
     
    /*for(var x = 0; x < 100; x++) 
    { 
         
    }*/ 
    //setTimeout(sendValues(ids), 500); 
}; 
 
var myWaitLoop = function(time, nb, fun) 
{ 
    if(nb > 0) 
    { 
        nb--; 
        fun(); 
        setTimeout(myWaitLoop(time, nb, fun), time*1000); 
    } 
}; 
 
//Get sensors ID 
var options = { 
    host: hostname, 
    port: port, 
    path: pathGetSensors, 
    method: 'GET', 
    headers: { 
        'Content-Type': 'application/json' 
    } 
}; 
 
sendRequest(options, 
        function(statusCode, result) 
        { 
            // I could work with the result html/json here. I could also just return it 
            console.log("onResult: (" + statusCode + ")\n\nData:\n" + JSON.stringify(result)); 
            //console.log("onResult: (" + statusCode + ")\n\nData:\n" + result); 
            console.log("\n\n"); 
            sendValues(result); 
        }, ""); 
         
//Send sensors values 
 
 
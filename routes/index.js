var util = require('util')
    , fs = require('fs')
    , gcm = require('node-gcm');

var registrationIds = [];

exports.index = function(req, res){
    res.render('index', { title: 'Express'});
};

exports.regist = function (req, res) {
	console.log("regId => "+req.body.regId);
	var reg_val =req.body.regId;
	registrationIds.push(reg_val);
};

exports.send_push = function(req, res) {
	var message = new gcm.Message();

	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');

	//var content_val = req.body.content_val;
	
	//console.log("Val => " +content_val);

	message.addDataWithKeyValue('content', 'Message Test');

	message.collapseKey = 'demo';
	message.delayWhileIdle = true;
	message.timeToLive = 3;

	sender.send(message, registrationIds, 4, function (err, result) {
		console.log(result);	
		res.send("success");
	});

};

exports.upload = function(req, res){
	console.log('->> upload was called!');
//	console.log('-> ' +  util.inspect(req.files.uploadedfile));        
	var images = [];

//	req.addListener('data', function(chunk) {
//		console.log('-> data ' + chunk);
//	});

	var image = req.files.uploadedfile;
	var kb = image.size / 1024 | 0;

	images.push({name: image.name, size: kb});
	renameImg(image);
 
//	console.log('->> render');
	res.send( { title: 'success', msg: 'Image Upload Done' });
};

function renameImg(image){
    var tmp_path = image.path;
	
	var file_name_array = image.name.split('/');
    var target_path = './public/upload/' + file_name_array[3];
//  console.log('->> tmp_path: ' + tmp_path );
    console.log('->> target_path: ' + target_path );
            
    fs.rename(tmp_path, target_path, function(err){
        if(err) throw err;
        console.log('->> upload done');
    });
}


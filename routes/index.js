var util = require('util')
    , fs = require('fs')
    , gcm = require('node-gcm');

var reg1 = null;

exports.index = function(req, res){
    res.render('index', { title: 'Express'});
};

exports.regist = function (req, res) {
	console.log("regId => "+req.body.regId);
	reg1 =req.body.regId;
	send_push2();
};


function send_push2() {
	var message = new gcm.Message();

	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');
	var registrationIds = [];

	/*
	message.addDataWithObject({
		key1: 'message1',
		key2: 'message2'
	});
	*/
	message.addDataWithKeyValue('key1','message11111');
	message.addDataWithKeyValue('key2','message22222입니다');

	message.collapseKey = 'demo';
	message.delayWhileIdle = true;
	message.timeToLive = 3;

	registrationIds.push(reg1);
	registrationIds.push('regId2');

	sender.send(message, registrationIds, 4, function (err, result) {
		console.log(result);	
	});

}

exports.send_push = function(req, res) {
	var message = new gcm.Message();

	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');
	var registrationIds = [];

	message.addDataWithObject({
		key1: 'message1',
		key2: 'message2'
	});

	message.collapseKey = 'demo';
	message.delayWhileIdle = true;
	message.timeToLive = 3;

	registrationIds.push('regId1');
	registrationIds.push('regId2');

	sender.send(message, registrationIds, 4, function (err, result) {
		console.log(result);	
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


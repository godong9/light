var util = require('util')
    , fs = require('fs')
    , gcm = require('node-gcm');

var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

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
	var evt = new EventEmitter();
	var dao_t = require('../sql/timeline');
	
	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');
	
	var params = {
					email: req.session.email,
					nickname: req.session.nickname,
					group_id: req.session.group_id,
					type: req.body.type,
					pre_content: req.body.pre_content,
					content: req.body.content,
					calorie: req.body.calorie
	};
	
	console.log("content => " +params['content']);

	dao_t.dao_set_timeline(evt, mysql_conn, params);

	evt.on('set_timeline', function(err, rows){
		if(err) throw err;
		
		message.addDataWithKeyValue('nickname', params['nickname']);
		message.addDataWithKeyValue('view_type', params['type']);
		message.addDataWithKeyValue('pre_content', params['pre_content']);
		message.addDataWithKeyValue('content', params['content']);

		message.collapseKey = 'light';
		message.delayWhileIdle = false;
		message.timeToLive = 600;

		sender.send(message, registrationIds, 4, function (err, result) {
			console.log(result);	
			var result_val = { result:"success", msg:"회원가입이 완료되었습니다!" };
			res.send(result_val);
		});
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


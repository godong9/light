var util = require('util')
    , fs = require('fs')
    , gcm = require('node-gcm');

var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

exports.index = function(req, res){
    res.render('index', { title: 'Express'});
};

exports.regist = function (req, res) {
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	console.log("regId => "+req.body.regId);
	var reg_id =req.body.regId;
	var email = req.body.email;
	
	var params = {
		email: email,
		reg_id: reg_id								
	};
	
	//registrationIds.push(reg_id);

	dao_u.dao_regist_reg_id(evt, mysql_conn, params);
	
	evt.on('regist_reg_id', function(err, rows){
		if(err) throw err;
		console.log("Regist Success!");
		var result_val = { result:"success", msg:"reg_id 등록 완료!" };
		res.send(result_val);
	});
};

exports.update_reg_id = function (req, res) {
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	console.log("regId => "+req.body.regId);
	var reg_id =req.body.reg_id;
	var email = req.body.email;
	
	var params = {
		email: email,
		reg_id: reg_id								
	};

	dao_u.dao_regist_reg_id(evt, mysql_conn, params);
	
	evt.on('regist_reg_id', function(err, rows){
		if(err) throw err;
		console.log("Regist Update Success!");
		var result_val = { result:"success", msg:"reg_id 업데이트 완료!" };
		res.send(result_val);
	});
};

exports.send_push = function(req, res) {
	var message = new gcm.Message();
	var evt = new EventEmitter();
	var dao_t = require('../sql/timeline');

	var registrationIds = [];

	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');
	
	var user_email = req.session.email;

	var group_params = {
					group_id: req.session.group_id
	};

	var params = {
					email: req.session.email,
					nickname: req.session.nickname,
					group_id: req.session.group_id,
					type: req.body.type,
					pre_content: req.body.pre_content,
					content: req.body.content,
					calorie: req.body.calorie
	};

	//유저 이메일로 그룹 찾고 그 그룹에 해당하는 reg_id 가져오기
	//registrationIds.push(reg_id);

	dao_t.dao_get_group_reg_id(evt, mysql_conn, group_params);

	evt.on('get_group_reg_id', function(err, rows){
		if(err) throw err;
		
		for(var i=0; i<rows.length; i++) {
			var tmp_str = rows[i].reg_id;
			//tmp_str = tmp_str.replace(/\'/gi, '');
			console.log(rows[i].reg_id);
			registrationIds.push(tmp_str);		
		}
		dao_t.dao_set_timeline(evt, mysql_conn, params);
	});

	evt.on('set_timeline', function(err, rows){
		if(err) throw err;

		var today = getTimeStamp();

		console.log('Today => ' + today);

		message.addDataWithKeyValue('nickname', params['nickname']);
		message.addDataWithKeyValue('type', params['type']);
		message.addDataWithKeyValue('pre_content', params['pre_content']);
		message.addDataWithKeyValue('content', params['content']);
		message.addDataWithKeyValue('calorie', params['calorie']);
		message.addDataWithKeyValue('date', today);

		message.collapseKey = 'light';
		message.delayWhileIdle = false;
		message.timeToLive = 600;
		
		console.log('PUSHED DATA => '+params['nickname']+' // '+params['content']);

		sender.send(message, registrationIds, 4, function (err, result) {
			console.log(result);	
			var result_val = { result:"success", msg:"메시지 전송 완료!" };
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

function getTimeStamp() {
  var d = new Date();

  var s =
    leadingZeros(d.getFullYear(), 4) + '-' +
    leadingZeros(d.getMonth() + 1, 2) + '-' +
    leadingZeros(d.getDate(), 2) + ' ' +

    leadingZeros(d.getHours(), 2) + ':' +
    leadingZeros(d.getMinutes(), 2) + ':' +
    leadingZeros(d.getSeconds(), 2);

  return s;
}

function leadingZeros(n, digits) {
  var zero = '';
  n = n.toString();

  if (n.length < digits) {
    for (i = 0; i < digits - n.length; i++)
      zero += '0';
  }
  return zero + n;
}
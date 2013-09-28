var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

/*
라이벌 페이지 관련 함수들
*/

// 라이벌 페이지 정보 가져오는 함수
exports.rival_page_info = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.session.email;

	console.log("email(routes) => "+req.session.email);

	var params = { email: email };
	var result = { group_info:{}, user_info:{} };

	dao_r.dao_group_info(evt, mysql_conn, params);

	evt.on('group_info', function(err, rows){
		if(err) throw err;
		result.group_info = rows;
		console.log("group_info: "+rows);
		params['group_id'] = rows[0].group_id;
		dao_r.dao_user_info(evt, mysql_conn, params);
	});

	evt.on('user_info', function(err, rows){
		if(err) throw err;
		result.user_info = rows;
		res.send(result);
	});
};

//라이벌 다이얼로그 옷장 목록 가져오는 함수
exports.rival_closet_list = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.body.email;

	var params = { email: email };
	var result = { closet_list:{} };

	dao_r.dao_rival_closet_list(evt, mysql_conn, params);

	evt.on('closet_list', function(err, rows){
		if(err) throw err;
		result.closet_list = rows;
		res.send(result);
	});
};

//라이벌 다이얼로그 히스토리 데이터 가져오는 함수
exports.rival_history_data = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.body.email;

	var params = { email: email };
	var result = { history_data:{} };

	dao_r.dao_rival_history_data(evt, mysql_conn, params);

	evt.on('history_data', function(err, rows){
		if(err) throw err;
		result.history_data = rows;
		res.send(result);
	});
};

//라이벌 다이얼로그 말풍선 업데이트
exports.set_chat_data = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.session.email;
	var chat_val = req.body.chat_val;

	console.log("chat_data(chat_val) => "+chat_val);

	var params = { email: email, chat_val: chat_val };

	dao_r.dao_set_chat_data(evt, mysql_conn, params);

	evt.on('chat_data', function(err, rows){
		if(err) throw err;
		result = { result:"success", msg:"chat 데이터 업데이트!" };
		res.send(result);
	});
};

//매칭 상태 가져옴
exports.matching_status = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.session.email;

	var params = { email: email };
	var result = {  };

	dao_r.dao_get_matching_status(evt, mysql_conn, params);

	evt.on('matching_status', function(err, rows){
		if(err) throw err;
		console.log("status: "+rows[0].matching_status);
		result = { result:"success", matching_status: rows[0].matching_status };
		res.send(result);
	});
};

//캐릭터 옷 교체
exports.rival_change_clothes = function(req, res){
	var evt = new EventEmitter();
	var dao_r = require('../sql/rival');

	var email = req.session.email;
	var clothes = req.body.clothes;

	var params = { email: email, clothes: clothes };

	dao_r.dao_set_change_clothes(evt, mysql_conn, params);

	evt.on('change_clothes', function(err, character){
		if(err) throw err;
		result = { result:"success", msg:"옷 교체 완료!", character: character };
		res.send(result);
	});
};

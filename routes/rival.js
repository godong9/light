var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

/*
라이벌 페이지 관련 함수들
*/

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

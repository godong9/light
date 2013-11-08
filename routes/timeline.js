var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

var _TIMELINE_COMPLETE_FLAG_CNT = 2;

/*
타임라인 페이지 관련 함수들
*/
exports.timeline_data = function(req, res){
	var evt = new EventEmitter();
	var dao_t = require('../sql/timeline');

	var email = req.session.email; 
	var group_id = req.session.group_id;
	var start_date = req.body.start_date;
	var end_date = req.body.end_date;
	
	var complete_flag = 0;

//	console.log("group_id(routes_timeline) => "+req.session.group_id);
//	console.log("start_date(routes_timeline) => "+req.body.start_date);
//	console.log("end_date(routes_timeline) => "+req.body.end_date);
	
	var params = { email: email, group_id: group_id, start_date: start_date, end_date: end_date };
	var result = { timeline_data:{}, my_email:{ my_email: email }, weight_data:{} };

	dao_t.dao_timeline_data(evt, mysql_conn, params);
	dao_t.dao_get_weight_data(evt, mysql_conn, params);

	evt.on('timeline_data', function(err, rows){
		if(err) throw err;
		result.timeline_data = rows;
		console.log("timeline_data: "+rows);
		console.log("timeline_data: "+result.my_email);
		complete_flag++;
		if(complete_flag == _TIMELINE_COMPLETE_FLAG_CNT){
			res.send(result);
		}
		
	});

	evt.on('get_weight_data', function(err, rows){
		if(err) throw err;
		result.weight_data = rows;
		console.log("weight_data: "+rows);
		complete_flag++;
		if(complete_flag == _TIMELINE_COMPLETE_FLAG_CNT){
			res.send(result);
		}
	});
};


exports.get_fitbit  = function(req, res){
	var evt = new EventEmitter();
	var dao_t = require('../sql/timeline');
	var result = {};
	
	var email = req.session.email;
	var group_id = req.session.group_id;
	var tmp_date = req.body.tmp_date;

	var params = { 
		email: email,
		group_id: group_id,
		tmp_date: tmp_date
	}

	dao_t.dao_get_fitbit(evt, mysql_conn, params);
	evt.on('get_fitbit', function(err, rows, activity_cal){
		result = { result:"success", activity_cal: activity_cal };
		res.send(result);
	});
};
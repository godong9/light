var mysql_conn = require('./sql/mysql_server').mysql_conn;
var schedule = require('node-schedule');

var d0 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 0}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 0 Insert!");
	});
});

var m0 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 0}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '측정';
	var content = '몸무게 측정해서 기록하기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Sunday Mission Insert!");
	});
});

var d1 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 1}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 1 Insert!");
	});
});

var m1 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 1}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '식단';
	var content = '평소 양의 2/3만큼만 먹기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Monday Mission Insert!");
	});
});

var d2 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 2}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 2 Insert!");
	});
});

var m2 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 2}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '식단';
	var content = '인스턴트 음식 먹지 않기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Tuesday Mission Insert!");
	});
});

var d3 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 3}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 3 Insert!");
	});
});

var m3 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 3}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '운동';
	var content = '엘리베이터 이용하지 않기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Wednesday Mission Insert!");
	});
});

var d4 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 4}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 4 Insert!");
	});
});

var m4 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 4}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '식단';
	var content = '기름진 음식 줄이기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Thursday Mission Insert!");
	});
});

var d5 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 5}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 5 Insert!");
	});
});

var m5 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 5}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '운동';
	var content = '가까운 거리는 걸어가기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Friday Mission Insert!");
	});
});

var d6 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 6}, function(){
    var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 0;
	var content = '0일째';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Day 6 Insert!");
	});
});

var m6 = schedule.scheduleJob({hour: 09, minute: 00, dayOfWeek: 6}, function(){
	var email = 'allightdiet@gmail.com';
	var group_id = 0;
	var view_type = 2;
	var pre_content = '운동';
	var content = '30분 이상 걷기!';

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+email+"', ";
	sql += "`group_id` = '"+group_id+"', ";
	sql += "`view_type` = '"+view_type+"', ";
	sql += "`pre_content` = '"+pre_content+"', ";
	sql += "`content` = '"+content+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		console.log("Saturday Mission Insert!");
	});
});
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

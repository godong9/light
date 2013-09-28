var mysql_conn = require('./sql/mysql_server').mysql_conn;
var EventEmitter = require('events').EventEmitter;
var schedule = require('node-schedule');
var util = require('util');
var fs = require('fs');
var gcm = require('node-gcm');

// Sunday Job
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

var h0 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 0}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 0 Reset!");
		});
	});
});

var c0 = schedule.scheduleJob({hour: 00, minute: 01, dayOfWeek: 0}, function(){
	//기간 만료됐는지 확인하는 함수
});

/*
var p0 = schedule.scheduleJob({hour: 12, minute: 00, dayOfWeek: 0}, function(){
	//푸시 관련 함수
	
	var message = new gcm.Message();
	var evt = new EventEmitter();
	var dao_r = require('./sql/rival');

	var registrationIds = [];

	var sender = new gcm.Sender('AIzaSyBHorBmc3UVUlEMte5icgua25nmh9671yY');
	
	var group_params = {
		group_id: 1
	};

	dao_r.dao_get_group_reg(evt, mysql_conn, group_params);

	evt.on('get_group_reg', function(err, rows){
		if(err) throw err;
		
		for(var i=0; i<rows.length; i++) {
			var tmp_str = rows[i].reg_id;
			console.log(rows[i].reg_id);
			registrationIds.push(tmp_str);		
		}
		
		var today = Date.now();

		message.addDataWithKeyValue('nickname', '관리자');
		message.addDataWithKeyValue('type', '매칭');
		message.addDataWithKeyValue('pre_content', '매칭관련');
		message.addDataWithKeyValue('content', '라이벌 매칭이 완료되었습니다!');
		message.addDataWithKeyValue('calorie', '0');
		message.addDataWithKeyValue('date', today);		
		
		message.collapseKey = 'light';
		message.delayWhileIdle = false;
		message.timeToLive = 600;
		
		sender.send(message, registrationIds, 4, function (err, result) {
			console.log(result);	
			var result_val = { result:"success", msg:"메시지 전송 완료!" };
		});
	});
});
*/

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

// Monday Job
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

var h1 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 1}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 1 Reset!");
		});
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

var h2 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 2}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 2 Reset!");
		});
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

var h3 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 3}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 3 Reset!");
		});
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

var h4 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 4}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 4 Reset!");
		});
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

var h5 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 5}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 5 Reset!");
		});
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

var h6 = schedule.scheduleJob({hour: 00, minute: 00, dayOfWeek: 6}, function(){
	var sql = "SELECT ";
		sql += "`email`, "; 
		sql += "`day_calorie`, "; 
		sql += "`food_calorie`, "; 
		sql += "`exercise_calorie` ";
		sql += "FROM `user` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		// for 문 안에서 비동기 처리
		for (var i=1; i<rows.length; i++)
		{
			(function (i) {
				console.log(i);
				var tmp_email = rows[i].email;
				var tmp_day = rows[i].day_calorie;
				var tmp_food = rows[i].food_calorie;
				var tmp_exercise = rows[i].exercise_calorie;
				var tmp_status_num = parseInt( Number(tmp_day) - (Number(tmp_food)-Number(tmp_exercise)) );
				var tmp_status;
				if(tmp_status_num > 0){
					tmp_status = "good";
				}
				else{
					tmp_status = "bad";
				}
				var i_sql = "INSERT INTO `user_history` ";
				i_sql += "SET `email` = '"+tmp_email+"', ";
				i_sql += "`status` = '"+tmp_status+"', ";
				i_sql += "`food_calorie` = '"+tmp_food+"', ";
				i_sql += "`exercise_calorie` = '"+tmp_exercise+"' ";

				var i_query = mysql_conn.query(i_sql, function(err, rows, fields) {
					console.log("tmp_email =>"+tmp_email);
					console.log("tmp_status=>"+tmp_status);
					console.log("tmp_food =>"+tmp_food);
				});
			}(i));
		}

		var u_sql = "UPDATE `user` ";
		u_sql += "SET `food_calorie` = '0', "; 
		u_sql += "`exercise_calorie` = '0' "; 
		
		var u_query = mysql_conn.query(u_sql, function(err, rows, fields) {
			console.log("Day 6 Reset!");
		});
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

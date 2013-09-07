// dao_timeline_data
// params['email']
// params['group_id']
// params['start_date']
// params['end_date']
exports.dao_timeline_data = function(evt, mysql_conn, params){
	var start_time = params['start_date']+' 00:00:00';
	var end_time = params['end_date']+' 23:59:59';
	
	console.log("group_id=>"+params['group_id']);

	var sql = "SELECT ";
	sql +="`A`.`email`, ";
	sql +="`A`.`reg_date`, ";
	sql += "`A`.`view_type`, ";
	sql += "`A`.`pre_content`, ";
	sql += "`A`.`content`, ";
	sql += "`A`.`calorie`, ";
	sql += "`B`.`nickname` ";
	sql += "FROM `timeline` AS `A` ";
	sql += "INNER JOIN `user` AS `B` ";
	sql += "ON `A`.`email` = `B`.`email` ";
	sql += "WHERE `A`.`group_id` = '"+params['group_id']+"' AND ";
	sql += "`A`.`reg_date` between timestamp('"+start_time+"') and ";
	sql += "timestamp('"+end_time+"') ";
	sql += "ORDER BY `A`.`reg_date` DESC";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('timeline_data', err, rows);
	});
	return sql;
}

// dao_get_weight_data
// params['email']
exports.dao_get_weight_data = function(evt, mysql_conn, params){

	var sql = "SELECT ";
	sql +="`A`.`email`, ";
	sql +="`A`.`nickname`, ";
	sql += "`A`.`weight`, ";
	sql += "`A`.`height` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('get_weight_data', err, rows);
	});
	return sql;
}

// dao_get_group_reg_id
// params['group_id']
exports.dao_get_group_reg_id = function(evt, mysql_conn, params){

	var sql = "SELECT ";
	sql +="`A`.`reg_id` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`group_id` = '"+params['group_id']+"' ";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('get_group_reg_id', err, rows);
	});
	return sql;
}

// dao_set_timeline
// params['email']
// params['nickname']
// params['group_id']
// params['type']
// params['pre_content']
// params['content']
// params['calorie']
exports.dao_set_timeline = function(evt, mysql_conn, params){
	if(params['type'] == '4'){	//식사 기록일 때
		//console.log("food Update");
		var pre_sql = "SELECT ";
		pre_sql += "`food_calorie` "; 
		pre_sql += "FROM `user` "; 
		pre_sql += "WHERE `email` = '"+params['email']+"' ";
		var pre_query = mysql_conn.query(pre_sql, params, function(err, rows, fields) {
			var tmp_food_calorie = Number(rows[0].food_calorie) + Number(params['calorie']);
			//console.log("food => "+tmp_food_calorie);				
			var u_sql = "UPDATE `user` ";
			u_sql += "SET `food_calorie` = '"+tmp_food_calorie+"' "; 
			u_sql += "WHERE `email` = '"+params['email']+"' ";
			var u_query = mysql_conn.query(u_sql, params, function(err, rows, fields) {
				//console.log("update food_calorie");	
			});
		}); 
	}
	else if(params['type'] == '5'){	//운동 기록일 때
		//console.log("exercise Update");
		var pre_sql = "SELECT ";
		pre_sql += "`exercise_calorie` "; 
		pre_sql += "FROM `user` "; 
		pre_sql += "WHERE `email` = '"+params['email']+"' ";
		var pre_query = mysql_conn.query(pre_sql, params, function(err, rows, fields) {
			var tmp_exercise_calorie = Number(rows[0].exercise_calorie) + Number(params['calorie']);
			//console.log("exercise => "+tmp_exericse_calorie);				
			var u_sql = "UPDATE `user` ";
			u_sql += "SET `exercise_calorie` = '"+tmp_exercise_calorie+"' "; 
			u_sql += "WHERE `email` = '"+params['email']+"' ";
			var u_query = mysql_conn.query(u_sql, params, function(err, rows, fields) {
				//console.log("update exercise_calorie");	
			});
		}); 
	}
	else if(params['type'] == '7'){	//체중 기록일 때
		console.log("weight Update");
		var pre_sql = "UPDATE `user` ";
		pre_sql += "SET `weight` = '"+params['content']+"' "; 
		pre_sql += "WHERE `email` = '"+params['email']+"' ";
		var pre_query = mysql_conn.query(pre_sql, params, function(err, rows, fields) {
			console.log("weight Update");	
		});
	}

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+params['email']+"', ";
	sql += "`group_id` = '"+params['group_id']+"', ";
	sql += "`view_type` = '"+params['type']+"', ";
	sql += "`pre_content` = '"+params['pre_content']+"', ";
	sql += "`content` = '"+params['content']+"', ";
	sql += "`calorie` = '"+params['calorie']+"'";
	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		console.log("insert Complete");
		evt.emit('set_timeline', err, rows);
	});
	return sql;
}

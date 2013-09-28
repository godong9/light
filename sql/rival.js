// group_info
// params['email']
exports.dao_group_info = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`A`.`group_id`, ";
	sql += "`A`.`title`, ";
	sql += "`A`.`goal`, ";
	sql += "`A`.`start_date`, ";
	sql += "`A`.`end_date`, ";
	sql += "`A`.`reward`, ";
	sql += "`B`.`email`, ";
	sql += "`B`.`nickname` ";
	sql += "FROM `rival_group` AS `A` ";
	sql += "INNER JOIN `user` AS `B` ";
	sql += "ON `A`.`group_id` = `B`.`group_id` ";
	sql += "WHERE `B`.`email` = '"+params['email']+"' ";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('group_info', err, rows);
	});
	return sql;
}

// user_info
// params['email']
// params['group_id']
exports.dao_user_info = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`A`.`email`, ";
	sql +="`A`.`nickname`, ";
	sql += "`A`.`weight`, ";
	sql += "`A`.`height`, ";
	sql += "`A`.`pre_weight`, ";
	sql += "`A`.`goal_weight`, ";
	sql += "`A`.`character`, ";
	sql += "`A`.`chat_ballon`, ";
	sql += "`A`.`score`, ";
//	sql += "`A`.`calorie_status`, ";
	sql += "`A`.`day_calorie`, ";
	sql += "`A`.`food_calorie`, ";
	sql += "`A`.`exercise_calorie` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`group_id` = '"+params['group_id']+"' ";
	sql += "ORDER BY `A`.`email` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('user_info', err, rows);
	});
	return sql;
}

// rival_closet_list
// params['email']
exports.dao_rival_closet_list = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`A`.`title`, ";
	sql +="`A`.`clothes` ";
	sql += "FROM `user_closet` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";
	sql += "ORDER BY `A`.`reg_date` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('closet_list', err, rows);
	});
	return sql;
}


// rival_history_data
// params['email']
exports.dao_rival_history_data = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`A`.`status`, ";
	sql +="`A`.`food_calorie`, ";
	sql +="`A`.`exercise_calorie`, ";
	sql += "`A`.`reg_date` ";
	sql += "FROM `user_history` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";
	sql += "ORDER BY `A`.`reg_date` ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('history_data', err, rows);
	});
	return sql;
}

// set_chat_data
// params['email']
// params['chat_val']
exports.dao_set_chat_data = function(evt, mysql_conn, params){
	console.log("EMAIL=>"+params['email']);
	var sql = "UPDATE `user` ";
	sql += "SET `chat_ballon` = '"+params['chat_val']+"' "; 
	sql += "WHERE `email` = '"+params['email']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('chat_data', err, rows);
	});
	return sql;
}

// get_matching_status
// params['email']
exports.dao_get_matching_status = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`matching_status` ";
	sql += "FROM `user` ";
	sql += "WHERE `email` = '"+params['email']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('matching_status', err, rows);
	});
	return sql;
}

// set_change_clothes
// params['email']
// params['clothes']
exports.dao_set_change_clothes = function(evt, mysql_conn, params){

	//캐릭터 옷 변경할 때 옷만 교체하기 위해
	var pre_sql = "SELECT ";
	pre_sql += "`character` ";
	pre_sql += "FROM `user` "; 
	pre_sql += "WHERE `email` = '"+params['email']+"' ";
	var pre_query = mysql_conn.query(pre_sql, params, function(err, rows, fields) {
		var ch_rows = rows[0].character;
		var ch_array = ch_rows.split('_');
		var ch_0 = ch_array[0];	//캐릭터
		var ch_1 = ch_array[1];	//옷
		var ch_2 = ch_array[2];	//상태
		var new_ch_1 = params['clothes'];
		var new_ch_array = 0;

		new_ch_array = ch_0+'_'+new_ch_1+'_'+ch_2;
	
		var u_sql = "UPDATE `user` ";
		u_sql += "SET `character` = '"+new_ch_array+"' ";
		u_sql += "WHERE `email` = '"+params['email']+"' ";
		var u_query = mysql_conn.query(u_sql, params, function(err, rows, fields) {
			console.log("Change Closthes");	
			var character = new_ch_array;
			evt.emit('change_clothes', err, character);
		});
	}); 
}

// get_group_reg
// params['group_id']
exports.dao_get_group_reg = function(evt, mysql_conn, params){
	var sql = "SELECT ";
	sql +="`A`.`reg_id` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`group_id` = '"+params['group_id']+"' ";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('get_group_reg', err, rows);
	});
	return sql;
}
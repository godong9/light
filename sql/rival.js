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
	sql += "`A`.`goal_weight`, ";
	sql += "`A`.`character`, ";
	sql += "`A`.`chat_ballon`, ";
	sql += "`A`.`score`, ";
	sql += "`A`.`calorie_status`, ";
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
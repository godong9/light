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
	sql += "`A`.`weight`, ";
	sql += "`A`.`height` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";
	
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('get_weight_data', err, rows);
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

	var sql = "INSERT INTO `timeline` ";
	sql += "SET `email` = '"+params['email']+"', ";
	sql += "`group_id` = '"+params['group_id']+"', ";
	sql += "`view_type` = '"+params['type']+"', ";
	sql += "`pre_content` = '"+params['pre_content']+"', ";
	sql += "`content` = '"+params['content']+"', ";
	sql += "`calorie` = '"+params['calorie']+"'";
	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		evt.emit('set_timeline', err, rows);
	});
	return sql;
}

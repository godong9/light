// community_data
// 
exports.dao_community_data = function(evt, mysql_conn, params){
	/*
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
	*/
	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('community_data', err, rows);
	});
	return sql;
}
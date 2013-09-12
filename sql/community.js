// community_data
// 
exports.dao_community_data = function(evt, mysql_conn, params){
	
	var sql = "SELECT ";
	sql +="`A`.`type`, ";
	sql +="`A`.`post_idx`, ";
	sql += "`A`.`title`, ";
	sql += "`A`.`content`, ";
	sql += "`A`.`hits`, ";
	sql += "`A`.`num_comment`, ";
	sql += "`A`.`reg_date`, ";
	sql += "`B`.`nickname` ";
	sql += "FROM `community` AS `A` ";
	sql += "INNER JOIN `user` AS `B` ";
	sql += "ON `A`.`email` = `B`.`email` ";
	sql += "ORDER BY `A`.`reg_date` DESC";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('community_data', err, rows);
	});
	return sql;
}
// community_data
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

// community_write
exports.dao_community_write = function(evt, mysql_conn, params){

	var sql = "INSERT INTO `community` ";
	sql += "SET `email` = '"+params['email']+"', ";
	sql += "`type` = '"+params['type']+"', ";
	sql += "`title` = '"+params['title']+"', ";
	sql += "`content` = '"+params['content']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('community_write', err, rows);
	});
	return sql;
}

// comment_data
exports.dao_comment_data = function(evt, mysql_conn, params){
	
	var sql = "SELECT ";
	sql +="`A`.`post_idx`, ";
	sql += "`A`.`content`, ";
	sql += "`A`.`reg_date`, ";
	sql += "`B`.`nickname` ";
	sql += "FROM `community_comment` AS `A` ";
	sql += "INNER JOIN `user` AS `B` ";
	sql += "ON `A`.`email` = `B`.`email` ";
	sql += "WHERE `A`.`post_idx` = '"+params['post_idx']+"' ";
	sql += "ORDER BY `A`.`reg_date` DESC";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('comment_data', err, rows);
	});
	return sql;
}

// comment_write
exports.dao_comment_write = function(evt, mysql_conn, params){

	var sql = "INSERT INTO `community_comment` ";
	sql += "SET `post_idx` = '"+params['post_idx']+"', ";
	sql += "`email` = '"+params['email']+"', ";
	sql += "`content` = '"+params['content']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		var u_sql = "UPDATE `community` ";
			u_sql += "SET `num_comment` = `num_comment` + 1 ";
			u_sql += "WHERE `post_idx` = '"+params['post_idx']+"' ";
		var u_query = mysql_conn.query(u_sql, params, function(err, rows, fields) {
			console.log("num_comment Update");	
		});
		evt.emit('comment_write', err, rows);
	});
	return sql;
}

// community_hits
exports.dao_community_hits = function(evt, mysql_conn, params){
	var u_sql = "UPDATE `community` ";
		u_sql += "SET `hits` = `hits` + 1 ";
		u_sql += "WHERE `post_idx` = '"+params['post_idx']+"' ";
	var u_query = mysql_conn.query(u_sql, params, function(err, rows, fields) {
		var sql = "SELECT ";
			sql += "`hits` ";
			sql += "FROM `community` ";
			sql += "WHERE `post_idx` = '"+params['post_idx']+"' ";
		var query = mysql_conn.query(sql, function(err, rows, fields) {
			evt.emit('community_hits', err, rows);
		});		
	});
	return u_sql;
}

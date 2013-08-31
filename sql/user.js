// check_email
// params['email']
exports.dao_check_email = function(evt, mysql_conn, params){
	var sql = "SELECT	COUNT(*) AS `cnt` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('check_email', err, rows);
	});
	return sql;
}

// check_nickname
// params['nickname']
exports.dao_check_nickname = function(evt, mysql_conn, params){
	var sql = "SELECT	COUNT(*) AS `cnt` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`nickname` = '"+params['nickname']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('check_nickname', err, rows);
	});
	return sql;
}

// join
// params['email']
// params['password']
// params['nickname']
exports.dao_join = function(evt, mysql_conn, params){
	var sql = "INSERT INTO `user` ";
	sql += "SET `email` = '"+params['email']+"', ";
	sql += "`password` = md5('"+params['password']+"'), ";
	sql += "`nickname` = '"+params['nickname']+"', ";
	sql += "`group_id` = '"+params['group_id']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('join', err, rows);
	});
	return sql;
}

// login
// params['email']
// params['password']
exports.dao_login = function(evt, mysql_conn, params){
	var sql = "SELECT	`A`.`email`, ";
	sql += "`A`.`password`, ";
	sql += "`A`.`nickname`, ";
	sql += "`A`.`group_id`, ";
	sql += "`A`.`weight`, ";
	sql += "`A`.`height`, ";
	sql += "`A`.`goal_weight`, ";
	sql += "`A`.`character`, ";
	sql += "`A`.`score`, ";
	sql += "md5('"+params['password']+"') AS `input_password` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['email']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('login', err, rows);
	});
	return sql;
}

// regist_reg_id
// params['email']
// params['reg_id']
exports.dao_regist_reg_id = function(evt, mysql_conn, params){

	console.log("EMAIL=>"+params['email']);
	var sql = "UPDATE `user` ";
	sql += "SET `reg_id` = '"+params['reg_id']+"' "; 
	sql += "WHERE `email` = '"+params['email']+"' ";
	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		console.log("Regist!");
		evt.emit('regist_reg_id', err, rows);	
	});
	return sql;
}

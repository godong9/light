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
	//console.log("EMAIL=>"+params['email']);
	var sql = "UPDATE `user` ";
	sql += "SET `reg_id` = '"+params['reg_id']+"' "; 
	sql += "WHERE `email` = '"+params['email']+"' ";
	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		console.log("Regist!");
		evt.emit('regist_reg_id', err, rows);	
	});
	return sql;
}

// profile
// params['email']
// params['gender']
// params['height']
// params['weight']
// params['goal']
exports.dao_profile = function(evt, mysql_conn, params){
	console.log("profile EMAIL=>"+params['email']);
	var sql = "UPDATE `user` ";
	sql += "SET `gender` = '"+params['gender']+"', "; 
	sql += "`height` = '"+params['height']+"', "; 
	sql += "`weight` = '"+params['weight']+"', "; 
	sql += "`start_weight` = '"+params['weight']+"', "; 
	sql += "`pre_weight` = '"+params['weight']+"', "; 
	sql += "`goal_weight` = '"+params['goal']+"' ";
	sql += "WHERE `email` = '"+params['email']+"' ";

	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		evt.emit('profile', err, rows);	
	});
	return sql;
}

// matching
// params['email']
// params['goal']
// params['term']
exports.dao_matching = function(evt, mysql_conn, params){
	console.log("matching EMAIL=>"+params['email']);
	var sql = "UPDATE `user` ";
	sql += "SET `matching_term` = '"+params['term']+"', "; 
	sql += "`matching_goal` = '"+params['goal']+"' "; 
	sql += "WHERE `email` = '"+params['email']+"' ";
	var query = mysql_conn.query(sql, params, function(err, rows, fields) {
		evt.emit('matching', err, rows);	
	});
	return sql;
}

// set_clothes
// params['email']
exports.dao_set_clothes = function(evt, mysql_conn, params){
	var sql1 = "INSERT INTO `user_closet` ";
	sql1 += "SET `email` = '"+params['email']+"', ";
	sql1 += "`title` = '베이직룩', ";
	sql1 += "`clothes` = '1' ";

	var query1 = mysql_conn.query(sql1, function(err, rows, fields) {
		console.log("ADD Clothes 1");
		var sql2 = "INSERT INTO `user_closet` ";
			sql2 += "SET `email` = '"+params['email']+"', ";
			sql2 += "`title` = '캠퍼스룩', ";
			sql2 += "`clothes` = '2' ";

		var query2 = mysql_conn.query(sql2, function(err, rows, fields) {
			console.log("ADD Clothes 2");
		});
	});
	return sql1;
} 


// check_email
// params['id']
exports.dao_check_email = function(evt, mysql_conn, params){
	// group
	var sql = "SELECT	COUNT(*) AS `cnt` ";
	sql += "FROM `user` AS `A` ";
	sql += "WHERE `A`.`email` = '"+params['id']+"' ";

	var query = mysql_conn.query(sql, function(err, rows, fields) {
		evt.emit('check_email', err, rows);
	});
	return sql;
}
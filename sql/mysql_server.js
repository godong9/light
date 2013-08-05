var mysql      = require('mysql');
var mysql_conn = mysql.createConnection({
	host     : '211.110.61.51',
	user     : 'root',
	password : 'dmdtka',
	database: 'light'
});

module.exports = {
  mysql_conn: mysql_conn
}

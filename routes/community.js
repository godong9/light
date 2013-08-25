var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

// 커뮤니티 페이지 정보 가져오는 함수
exports.community_data = function(req, res){
	
	var evt = new EventEmitter();
	var dao_c = require('../sql/community');

	var params = { };
	var result = { community_data:{} };

	dao_c.community_data(evt, mysql_conn, params);

	evt.on('community_data', function(err, rows){
		if(err) throw err;
		result.community_data = rows;
		res.send(result);
	});

};
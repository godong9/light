var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

// 커뮤니티 페이지 정보 가져오는 함수
exports.community_data = function(req, res){
	
	var evt = new EventEmitter();
	var dao_c = require('../sql/community');

	var params = { };
	var result = { community_data:{} };

	dao_c.dao_community_data(evt, mysql_conn, params);

	evt.on('community_data', function(err, rows){
		if(err) throw err;
		result.community_data = rows;
		res.send(result);
	});
};

// 커뮤니티에 쓴 글 저장하는 함수
exports.community_write = function(req, res){
	
	var evt = new EventEmitter();
	var dao_c = require('../sql/community');

	var email = req.session.email; 	
	var type = req.body.type;
	var title = req.body.title;
	var content = req.body.content;
	var params = { 
		email: email, 
		type: type,
		title: title,
		content: content
	}

	var result = { };

	dao_c.dao_community_write(evt, mysql_conn, params);

	evt.on('community_write', function(err, rows){
		if(err) throw err;
		result = { result:"success", msg:"글 작성 완료!" };
		res.send(result);
	});
};

// 커뮤니티에 쓴 글 저장하는 함수
exports.comment_data = function(req, res){
	
	var evt = new EventEmitter();
	var dao_c = require('../sql/community');

	var post_idx = req.body.post_idx;
	var params = { 
		post_idx: post_idx
	}
	var result = { comment_data:{} };

	dao_c.dao_comment_data(evt, mysql_conn, params);

	evt.on('comment_data', function(err, rows){
		if(err) throw err;
		result.comment_data = rows;
		res.send(result);
	});
};

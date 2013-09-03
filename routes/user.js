var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;

/*
사용자 관련 처리 함수들
*/

function register_session(req, email, nickname, group_id)
{
	req.session.email = email;
	req.session.nickname = nickname;
	req.session.group_id = group_id;
}

exports.join = function(req, res){
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	var result = {};

	var email = req.body.email;
	var password = req.body.password;
	var re_password = req.body.re_password;
	var nickname = req.body.nickname;
	var group_id = req.body.group_id;

	req.checkBody("nickname", "닉네임을 입력해주세요!").notEmpty();
	req.checkBody("email", "email을 입력해주세요!").notEmpty();
	req.checkBody("email", "잘못된 email입니다!").isEmail();
	req.checkBody("password", "비밀번호를 입력해주세요!").notEmpty();
	req.checkBody("re_password", "비밀번호 확인란을 잘못 입력하였습니다!").equals(password);

	var errors = req.validationErrors();
	if( errors !== null )
	{
		result = errors[0];
		res.send(result);
	}
	else
	{
		var params = { email: email }
		dao_u.dao_check_email(evt, mysql_conn, params);
	}

	evt.on('check_email', function(err, rows){
		if( rows[0].cnt === 0 )
		{
			var params = { nickname: nickname };
			dao_u.dao_check_nickname(evt, mysql_conn, params);
		}
		else
		{
			result = { result:"failed", msg:"사용 중인 email입니다!" };
			res.send(result);
		}
	});

	evt.on('check_nickname', function(err, rows){
		if( rows[0].cnt === 0 )
		{
			var params = {
						email: email,
						password: password,
						nickname: nickname
			};
			dao_u.dao_join(evt, mysql_conn, params);
		}
		else
		{
			result = { result:"failed", msg:"사용 중인 닉네임입니다!" };
			res.send(result);
		}
	});

	evt.on('join', function(err, rows){
		console.log("JOIN Complete!");
		register_session(req, email, nickname);
		result = { result:"success", msg:"회원가입이 완료되었습니다!" };
		res.send(result);
	});

};

exports.login = function(req, res){
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	var result = {};

	var email = req.body.email;
	var password = req.body.password;

	var params = { 
		email: email, 
		password: password 
	}

	dao_u.dao_login(evt, mysql_conn, params);
	evt.on('login', function(err, rows){
		if(err)
			throw err;

		if( rows.length < 1 )
		{
			result = { result:"failed", msg:"email 또는 비밀번호가 잘못되었습니다!" };
			res.send(result);
		}
		else if( rows[0].password !== rows[0].input_password )
		{
			result = { result:"failed", msg:"email 또는 비밀번호가 잘못되었습니다!" };
			res.send(result);
		}
		else if( rows[0].password === rows[0].input_password )
		{
			
			register_session(req, rows[0].email, rows[0].nickname, rows[0].group_id);

			console.log("Session 등록 -> "+req.session.email);
			console.log("Session 등록 -> "+req.session.nickname);
			console.log("Session 등록 -> "+req.session.group_id);

			result = { result:"success", msg:"로그인 성공!" };
			res.send(result);
		}
	});
};

exports.profile = function(req, res){
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	var result = {};
	
	var email = req.session.email;
	var gender = req.body.gender;
	var height = req.body.height;
	var weight = req.body.weight;
	var goal = req.body.goal;

	var params = { 
		email: email, 
		gender: gender,
		height: height,
		weight: weight,
		goal: goal 
	}

	dao_u.dao_profile(evt, mysql_conn, params);
	evt.on('profile', function(err, rows){
		if(err)
			throw err;

		console.log("Profile Update!");

		result = { result:"success", msg:"프로필 업데이트 성공!" };
		res.send(result);
	});
};

exports.matching = function(req, res){
	var evt = new EventEmitter();
	var dao_u = require('../sql/user');
	var result = {};
	
	var email = req.session.email;
	var goal= req.body.goal;
	var term = req.body.term;

	var params = { 
		email: email, 
		goal: goal,
		term: term
	}

	dao_u.dao_matching(evt, mysql_conn, params);
	evt.on('matching', function(err, rows){
		if(err)
			throw err;

		console.log("Matching Start!");

		result = { result:"success", msg:"매칭정보 업데이트 성공!" };
		res.send(result);
	});
};

var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('../sql/mysql_server').mysql_conn;
/*
사용자 관련 처리 함수들
*/

function register_session(req, idx_user, email, nickname)
{
	req.session.idx_user = idx_user;
	req.session.email = email;
	req.session.nickname = nickname;
}

exports.join = function(req, res){
	var evt = new EventEmitter();
//	var dao_u = require('../sql/user');
	var result = {};

	var email = req.body.email;
	var password = req.body.password;
	var re_password = req.body.re_password;
	var nickname = req.body.nickname;


	console.log("닉네임: "+nickname);
	console.log("password: "+password);
	console.log("email: "+email);
	result = { result:"success", msg:"회원가입이 완료되었습니다!" };
	res.send(result);

/*
	req.checkBody("name", "닉네임을 입력해주세요!").notEmpty();
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
		var params = { id: email }
		dao_m.dao_check_email(evt, mysql_conn, params);
	}

	evt.on('check_email', function(err, rows){
		if( rows[0].cnt === 0 )
		{
			dao_m.dao_sign_up(evt, mysql_conn, params);
		}
		else
		{
			result = { result:"failed", msg:"사용 중인 email입니다!" };
			res.send(result);
		}
	});

	evt.on('sign_up', function(err, rows){
		console.log("sign_up");
		var idx_user = rows.insert_idx;
		//register_session(req, idx_user, sign_up_email, first_name, last_name);
		//sendMail(sign_up_email, code);
		result = { result:"success", msg:"회원가입이 완료되었습니다!" };
		res.send(result);
	});

	*/
};


/*
 * POST user login
 */

exports.login = function(req, res){
	var result;
	
	everyauth.password.authenticate('brian@example.com', 'password');

	result = { result:test, msg:"successful" };
	
	res.send(result);
	
};
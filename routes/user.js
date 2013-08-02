
/*
 * GET users listing.
 */

exports.list = function(req, res){
	var result;
	
	result = { result:"성공", msg:"successful" };
	
	res.send(result);
	
};
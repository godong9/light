
/*
 * GET users listing.
 */

exports.list = function(req, res){
	var result;
	
	result = { result:"successful", msg:"successful" };
	
	res.send(result);
	
};
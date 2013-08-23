var util = require('util')
    , fs = require('fs');

exports.index = function(req, res){
    res.render('index', { title: 'Express'});
};

exports.upload = function(req, res){
	console.log('->> upload was called!');
//	console.log('-> ' +  util.inspect(req.files.uploadedfile));        
	var images = [];

//	req.addListener('data', function(chunk) {
//		console.log('-> data ' + chunk);
//	});

	var image = req.files.uploadedfile;
	var kb = image.size / 1024 | 0;

	images.push({name: image.name, size: kb});
	renameImg(image);
 
//	console.log('->> render');
	res.send( { title: 'success', msg: 'Image Upload Done' });
};

function renameImg(image){
    var tmp_path = image.path;
	
	var file_name_array = image.name.split('/');
    var target_path = './public/upload/' + file_name_array[3];
//  console.log('->> tmp_path: ' + tmp_path );
    console.log('->> target_path: ' + target_path );
            
    fs.rename(tmp_path, target_path, function(err){
        if(err) throw err;
        console.log('->> upload done');
    });
}
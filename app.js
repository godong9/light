
/**
 * Module dependencies.
 */

var express = require('express')
	, routes = require('./routes')
    , user = require('./routes/user')
//  , login = require('./routes/login')
//  , rival = require('./routes/rival')
//  , timeline = require('./routes/timeline')
//  , community = require('./routes/community')
  , http = require('http')
  , path = require('path')
  , expressValidator = require('express-validator');

var app = express();

var validator_option = {
	errorFormatter: function(param, msg, value) {
		var namespace = param.split('.')
		, root    = namespace.shift()
		, formParam = root;

		while(namespace.length) {
			formParam += '[' + namespace.shift() + ']';
		}
		return {
			param : formParam,
			target : formParam,
			msg   : msg,
			value : value,
			result : "failed"
		};
	}
}

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(expressValidator(validator_option));
app.use(express.methodOverride());
app.use(express.cookieParser('keyboard cat'));
app.use(express.session());
app.use(app.router);
app.use(require('stylus').middleware(__dirname + '/public'));
app.use(express.static(path.join(__dirname, 'public')));
//파일 업로드 관련 설정
app.use(express.limit('10mb'));
app.use(express.bodyParser({uploadDir: __dirname + '/tmp'})); 
app.use(express.methodOverride());
app.use(express.logger({ buffer: 5000}));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}

//app.get('/', routes.index);
app.post('/join', user.join);
app.post('/login', user.login);

//파일 업로드 관련
app.post('/upload', routes.upload);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

module.exports = app;
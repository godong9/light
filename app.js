/**
 * Module dependencies.
 */

// 필요한 모듈들 추가
var express = require('express')
	, routes = require('./routes')
    , user = require('./routes/user')
	, rival = require('./routes/rival')
    , timeline = require('./routes/timeline')
    , community = require('./routes/community')
    , http = require('http')
    , path = require('path')
	, url = require('url')
    , expressValidator = require('express-validator');

var config = require('./config.json');
var Fitbit = require('fitbit');

var EventEmitter = require('events').EventEmitter;
var mysql_conn = require('./sql/mysql_server').mysql_conn;

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

// GET 방식으로 데이터 받을 때 처리해주는 함수 설정
app.get('/rival', rival.rival_page_info);
app.get('/community', community.community_data);
app.get('/img', function(req, res){	//이미지 파일 다운로드
	var url_parts = url.parse(req.url, true); // url 파싱
	var query = url_parts.query; // 쿼티로드 (ex> { id: 'zz3', a: '1', b: '2' } )
	var img_str = query.img_str;
	var file = __dirname + '/public/upload/' + img_str;
	res.download(file); // Set disposition and send it.
});
app.get('/matching_status', rival.matching_status);
app.get('/get_fitbit', timeline.get_fitbit);
app.get('/fitbit', function (req, res) {
	// Create an API client and start authentication via OAuth
	var client = new Fitbit(config.fitbit.CONSUMER_KEY, config.fitbit.CONSUMER_SECRET);
	client.getRequestToken(function (err, token, tokenSecret) {
		if (err) {
		  console.log(err);
		  return;
		}
		req.session.oauth = {
			requestToken: token
		  , requestTokenSecret: tokenSecret
		};
		res.redirect(client.authorizeUrl(token));
	});

});
// On return from the authorization
app.get('/oauth_callback', function (req, res) {
  var verifier = req.query.oauth_verifier
    , oauthSettings = req.session.oauth
    , client = new Fitbit(config.fitbit.CONSUMER_KEY, config.fitbit.CONSUMER_SECRET);
  // Request an access token
  client.getAccessToken(
      oauthSettings.requestToken
    , oauthSettings.requestTokenSecret
    , verifier
    , function (err, token, secret) {
        if (err) {
          console.log(err);
          return;
        }
        oauthSettings.accessToken = token;
        oauthSettings.accessTokenSecret = secret;

        res.redirect('/stats');
      }
  );
});
// Display some stats
app.get('/stats', function (req, res) {
  client = new Fitbit(
      config.fitbit.CONSUMER_KEY
    , config.fitbit.CONSUMER_SECRET
    , { // Now set with access tokens
          accessToken: req.session.oauth.accessToken
        , accessTokenSecret: req.session.oauth.accessTokenSecret
        , unitMeasure: 'en_GB'
      }
  );

  // Fetch todays activities
  client.getActivities(function (err, activities) {
    if (err) {
      console.log(err);
      return;
    }
	console.log("Activity");
    // `activities` is a Resource model
	
	var evt = new EventEmitter();
	var dao_f = require('./sql/fitbit');


	var params = { "activity_cal": activities.activityCalories(),
						"out_cal": activities.caloriesOut()
					}

	dao_f.dao_set_fitbit(evt, mysql_conn, params);

	evt.on('set_fitbit', function(err, rows){
		if(err) throw err;
		var result = { "activityCalories": activities.activityCalories(),
							"caloriesOut": activities.caloriesOut(),
							"caloriesBMR": activities.caloriesBMR(),
							"marginalCalories": activities.marginalCalories()
						}
		res.send(result);
	});

  });
});


// POST 방식으로 데이터 받을 때 처리해주는 함수 설정
app.post('/register', routes.regist);
app.post('/update_reg_id', routes.update_reg_id);
app.post('/join', user.join);
app.post('/login', user.login);
app.post('/timeline', timeline.timeline_data);
app.post('/closet_list', rival.rival_closet_list);
app.post('/rival_history', rival.rival_history_data);
app.post('/send_push', routes.send_push);
app.post('/profile', user.profile);
app.post('/matching', user.matching);
app.post('/chat', rival.set_chat_data);
app.post('/community_write', community.community_write);
app.post('/community_comment', community.comment_data);
app.post('/comment_write', community.comment_write);
app.post('/community_hits', community.community_hits);
app.post('/change_clothes', rival.rival_change_clothes);

//파일 업로드 관련
app.post('/upload', routes.upload);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

module.exports = app;
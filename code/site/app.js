//setup express to handle routing and rendering
var express = require('express');
var app = express();
app.set('view engine', 'ejs');

//setup bodyparser to extra data from form posts
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

//setup static public directory (to link images from view pages)
app.use(express.static('public'))

// route to index page
app.get('/', function(req, res) {
    res.render('page/index');
});

app.get('/documentation', function(req, res) {
    res.render('page/documentation');
});

app.get('/quickstart', function(req, res) {
    res.render('page/quickstart');
});

app.get('/feature1', function(req, res) {
    res.render('page/feature1');
});

app.get('/feature2', function(req, res) {
    res.render('page/feature2');
});

app.get('/feature3', function(req, res) {
    res.render('page/feature3');
});

app.get('/feature4', function(req, res) {
    res.render('page/feature4');
});

//start the web app
app.listen(8080, function () {
	console.log('App listening on port 8080!');
});


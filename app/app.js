//Setup sqlite to manage db connection/query
const sqlite3 = require('sqlite3').verbose();

//Define basic SQL query to fetch all TODO items
const fetchTodoQuery = 'SELECT * FROM TODO';
const insertTodoQuery = 'INSERT INTO TODO (Name, Description, Priority) VALUES (?, ?, ?)';
//Define basic SQL query to fetch all users items
const fetchUserQuery = 'SELECT * FROM USERS';
const insertUserQuery = 'INSERT INTO USERS (username, password) VALUES (?, ?)';
const loginUserQuery = 'SELECT * FROM USERS WHERE username = ? AND password = ?';

//Define function to connect to DB
const connectToDB = function () {
	return new sqlite3.Database('./todo-store.db', sqlite3.OPEN_READWRITE,
		(err) => {
			if (err) {
				console.error('Connection error:', err.message);
				process.exit(1)
			}
			console.log('Connected to the SQLite database.');
		}
	);
}

//Define function to close DB
const closeDB = function (db) {
	db.close((err) => {
		if (err) {
			return console.error('Closure error:', err.message);
		}
		console.log('Database connection closed.');
	});
}

//Define function to insert into DB - promise supports synchronous resolution
const insertTodo = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.run(sql, params, function (err) {
			if (err) {
				reject(err);
			}
			console.log('Inserted TODO Item with the following information.');
			console.log('name:', params[0]);
			console.log('description:', params[1]);
			console.log('priority:', params[2]);
			console.log('---');
			resolve();
		});
	});
};

//Define function to insert into DB - promise supports synchronous resolution
const insertUser = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.run(sql, params, function (err) {
			if (err) {
				reject(err);
				return;
			}
			console.log('Inserted USER with the following information.');
			console.log('username:', params[0]);
			console.log('password:', params[1]);
			console.log('---');
			resolve();
		});
	});
};

const fetchUser = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.get(sql, params, (err, row) => {
			if (err) {
				reject(err);
				return;
			}

			resolve(row);
		});
	});
};

//Define function to retrieve all TODO items in db - promise supports synchronous resolution
const fetchAll = async (db, sql, params) => {
	return new Promise((resolve, reject) => {
		db.all(sql, params, (err, rows) => {
			if (err) {
				reject(err);
			}

			todoList = []

			// using forEach function: Loop through rows if needed
			rows.forEach((row) => {
				todoList.push({ name: row.Name, description: row.Description, priority: row.Priority })
			});

			// using for loop: Loop through todoList to verify items in todoList going to index
			console.log("Retrieved the following TODO Items")
			for (const todoItem of todoList) {
				console.log(todoItem.name, todoItem.description, todoItem.priority)
			}
			console.log("---")

			resolve(todoList);
		});
	});
};

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
app.get('/', async function (req, res) {

	console.log("Running fetch")
	const db = new connectToDB();

	try {
		const todoList = await fetchAll(db, fetchTodoQuery);
		res.render('page/index', { data: todoList });
	} catch (err) {
		console.log(err);
		res.render('page/error');
	} finally {
		closeDB(db);
	}
});

// route to api call for checking a phone number
app.post('/api/add_todo', async function (req, res) {
	//setup params for query from form elements
	var name = req.body.name;
	var description = req.body.description;
	var priority = req.body.priority;
	const params = [name, description, priority]

	const db = new connectToDB();

	try {
		await insertTodo(db, insertTodoQuery, params);
		const todoList = await fetchAll(db, fetchTodoQuery);
		res.render('page/index', { data: todoList });
	} catch (err) {
		console.log(err);
		res.render('page/error');
	} finally {
		closeDB(db);
	}
});

// route to login page
app.get('/login', function(req, res) {
	res.render('page/login', {
		error: null
	});
});

// route to process login form
app.post('/login', async function(req, res) {

	var username = req.body.username;
	var password = req.body.password;

	const params = [username, password];

	const db = new connectToDB();

	try {
		const user = await fetchUser(db, loginUserQuery, params);

		if (user) {
			console.log('Login successful for:', username);

			res.render('page/landing', {
				username: user.username
			});
		} else {
			console.log('Login failed for:', username);

			res.render('page/login', {
				error: 'Invalid username or password'
			});
		}
	} catch (err) {
		console.log(err);
		res.render('page/error');
	} finally {
		closeDB(db);
	}
});

// route to create account page
app.get('/create', function(req, res) {
	res.render('page/create', {
		error: null
	});
});

const checkUserQuery = 'SELECT * FROM USERS WHERE LOWER(username) = LOWER(?)';

// route to process account creation form
app.post('/create', async function(req, res) {

	var username = req.body.username;
	var password = req.body.password;

	const db = new connectToDB();

	try {
		// Check if username already exists
		const existingUser = await fetchUser(db, checkUserQuery, [username]);

		if (existingUser) {
			console.log('Account creation failed. Username already exists:', username);

			res.render('page/create', {
				error: 'Username already exists'
			});

			return;
		}

		// Insert new user
		const params = [username, password];

		await insertUser(db, insertUserQuery, params);

		console.log('Account created for:', username);

		res.render('page/landing', {
			username: username
		});

	} catch (err) {
		console.log(err);

		res.render('page/create', {
			error: 'Unable to create account'
		});

	} finally {
		closeDB(db);
	}
});

//start the web app
app.listen(8080, function () {
	console.log('App listening on port 8080!');
});

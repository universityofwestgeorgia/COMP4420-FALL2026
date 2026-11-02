const sqlite3 = require('sqlite3').verbose();
const { authenticateUser, createAccount } = require('./auth');

let db;

function exec(sql) {
	return new Promise((resolve, reject) => {
		db.exec(sql, (error) => error ? reject(error) : resolve());
	});
}

function get(sql) {
	return new Promise((resolve, reject) => {
		db.get(sql, (error, row) => error ? reject(error) : resolve(row));
	});
}

beforeEach(async () => {
	db = new sqlite3.Database(':memory:');
	await exec('CREATE TABLE USERS (username TEXT PRIMARY KEY, password TEXT NOT NULL)');
});

afterEach(async () => {
	await new Promise((resolve, reject) => {
		db.close((error) => error ? reject(error) : resolve());
	});
});

module.exports = { getDb: () => db, exec, get, authenticateUser, createAccount };

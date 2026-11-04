const loginUserQuery = 'SELECT * FROM USERS WHERE username = ? AND password = ?';
const checkUserQuery = 'SELECT * FROM USERS WHERE LOWER(username) = LOWER(?)';
const insertUserQuery = 'INSERT INTO USERS (username, password) VALUES (?, ?)';

function findUser(db, query, params) {
	return new Promise((resolve, reject) => {
		db.get(query, params, (error, user) => {
			if (error) return reject(error);
			resolve(user);
		});
	});
}

function insertUser(db, username, password) {
	return new Promise((resolve, reject) => {
		db.run(insertUserQuery, [username, password], (error) => {
			if (error) return reject(error);
			resolve();
		});
	});
}

function authenticateUser(db, username, password) {
	return findUser(db, loginUserQuery, [username, password]);
}

async function createAccount(db, username, password) {
	const existingUser = await findUser(db, checkUserQuery, [username]);
	if (existingUser) return false;

	await insertUser(db, username, password);
	return true;
}

module.exports = { authenticateUser, createAccount };

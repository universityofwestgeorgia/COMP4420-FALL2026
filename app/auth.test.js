var { getDb, authenticateUser, createAccount } = require('./auth-checker');

test('creates an account and permits login with the correct credentials', async () => {
	expect(await createAccount(getDb(), 'leo', 'test')).toBe(true);
	expect(await authenticateUser(getDb(), 'leo', 'test')).toEqual({
		username: 'leo', password: 'test'
	});
});

test('rejects incorrect credentials and unknown users', async () => {
	await createAccount(getDb(), 'leo', 'test');
	expect(await authenticateUser(getDb(), 'leo', 'wrong')).toBeUndefined();
	expect(await authenticateUser(getDb(), 'tom', 'wrong')).toBeUndefined();
});

test('rejects duplicate usernames regardless of letter case', async () => {
	expect(await createAccount(getDb(), 'Leo', 'test')).toBe(true);
	expect(await createAccount(getDb(), 'leo', '2')).toBe(false);
	expect(await createAccount(getDb(), 'Leo', '3')).toBe(false);
	expect(await authenticateUser(getDb(), 'Leo', 'test')).toBeDefined();
});

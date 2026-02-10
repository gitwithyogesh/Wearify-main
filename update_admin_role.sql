-- Update the 'test' user to have ADMIN role
UPDATE users SET role = 'ADMIN' WHERE username = 'test';

-- If you are using a different username, replace 'test' with your username
-- UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';

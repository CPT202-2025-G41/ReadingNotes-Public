INSERT INTO users(username, password, email, role, is_non_locked, is_enabled) 
VALUES (
  'admin', 
  '{bcrypt}$2a$10$zuL2L7TI4OQz6B7Y4Jwj0eTzqkZ1XlD7nRj1xV1sL3bG9K5mYvH0K',
     -- admin123
  'admin@example.com', 
  'ADMIN', 
  true, 
  true
);

INSERT INTO users(username, password, email, role, is_non_locked, is_enabled) 
VALUES (
  'user', 
  '{bcrypt}$2a$10$Zzv8i5bWjIgQ7e5dMp5B3eT3qkZ1XlD7nRj1xV1sL3bG9K5mYvH0K', 
    -- user123
  'user@example.com', 
  'USER', 
  true, 
  true
);
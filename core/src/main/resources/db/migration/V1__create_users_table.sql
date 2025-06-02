-- Create sequence for users table
CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 1;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255) UNIQUE,
    username VARCHAR(255),
    password VARCHAR(255)
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
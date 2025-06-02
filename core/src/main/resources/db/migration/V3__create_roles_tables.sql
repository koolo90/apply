-- Create sequence for roles table
CREATE SEQUENCE IF NOT EXISTS roles_seq START WITH 1 INCREMENT BY 1;

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY DEFAULT nextval('roles_seq'),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (id, name) VALUES (nextval('roles_seq'), 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (nextval('roles_seq'), 'ROLE_ADMIN');

-- User-Role join table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- Create index on role name
CREATE INDEX IF NOT EXISTS idx_roles_name ON roles(name);

-- Create indexes for user_roles table
CREATE INDEX IF NOT EXISTS idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX IF NOT EXISTS idx_user_roles_role_id ON user_roles(role_id);

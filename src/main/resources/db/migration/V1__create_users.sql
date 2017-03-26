CREATE TABLE IF NOT EXISTS users(
  id BIGSERIAL PRIMARY KEY,
  login VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL
);

CREATE UNIQUE INDEX index_on_users_unique_lower_login
  ON users (lower(login));

CREATE UNIQUE INDEX index_on_users_unique_lower_email
  ON users (lower(email));

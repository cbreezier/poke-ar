CREATE TABLE IF NOT EXISTS users(
  id BIGSERIAL,
  oauth_name VARCHAR NOT NULL,
  username VARCHAR NOT NULL,

  money INTEGER NOT NULL DEFAULT 0,

  PRIMARY KEY(id),
  UNIQUE(oauth_name),
  UNIQUE(username)
);

ALTER TABLE pokemon ADD CONSTRAINT pokemon_owner_id_fk FOREIGN KEY (owner_id) REFERENCES users (id);

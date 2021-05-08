CREATE TABLE IF NOT EXISTS regions(
  id BIGSERIAL,
  locality VARCHAR NOT NULL,
  state VARCHAR NOT NULL,
  country VARCHAR NOT NULL,
  min_level INT NOT NULL,
  max_level INT NOT NULL,
  generation INT NOT NULL,
  gym_type VARCHAR NOT NULL,

  PRIMARY KEY(id),
  UNIQUE(locality, state, country)
);


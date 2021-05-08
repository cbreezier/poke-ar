CREATE TABLE IF NOT EXISTS regions(
  id BIGSERIAL,
  postcode VARCHAR NOT NULL,
  name VARCHAR NOT NULL,
  country VARCHAR NOT NULL,
  min_level INT NOT NULL,
  max_level INT NOT NULL,
  generation INT NOT NULL,
  gym_type VARCHAR NOT NULL,

  PRIMARY KEY(id),
  UNIQUE(postcode, country)
);


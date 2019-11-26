drop table if exists pokemon cascade;
create table pokemon(
  id SERIAL,
  name VARCHAR,

  PRIMARY KEY (id)
);

drop table if exists habitats cascade;
create table habitats(
  id SERIAL,
  habitat_type VARCHAR,
  rarity FLOAT,
  pokemon_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
);

drop table if exists spawns cascade;
create table spawns(
  id SERIAL,
  latitude REAL,
  longitude REAL,
  pokemon_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
)

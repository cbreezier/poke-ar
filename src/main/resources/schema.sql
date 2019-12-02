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
  world_x DOUBLE PRECISION,
  world_y DOUBLE PRECISION,
  pokemon_id INTEGER,
  start_timestamp BIGINT,
  end_timestamp BIGINT,

  PRIMARY KEY (id),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
)

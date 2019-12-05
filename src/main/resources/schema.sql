drop table if exists users cascade;
create table users(
                    id SERIAL,
                    name VARCHAR,

                    PRIMARY KEY (id)
);

drop table if exists pokedex cascade;
create table pokedex(
  id SERIAL, -- Should be int, and manually set
  name VARCHAR,

  PRIMARY KEY (id)
);

drop table if exists habitats cascade;
create table habitats(
  id SERIAL,
  habitat_type VARCHAR,
  rarity FLOAT,
  pokedex_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (pokedex_id) REFERENCES pokedex (id)
);

drop table if exists pokemon cascade;
create table pokemon(
  id SERIAL,
  pokedex_id INTEGER,
  hp INTEGER,
  exp INTEGER,
  bond_exp INTEGER,
  owner_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (pokedex_id) REFERENCES pokedex (id),
  FOREIGN KEY (owner_id) REFERENCES  users (id)
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
);

drop table if exists visited_locations cascade;
create table visited_locations(
  id SERIAL,
  world_x DOUBLE PRECISION,
  world_y DOUBLE PRECISION,
  timestamp BIGINT,

  PRIMARY KEY (id),
  UNIQUE (world_x, world_y)
);

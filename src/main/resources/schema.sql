drop table if exists users cascade;
create table users(
  id SERIAL,
  name VARCHAR,
  money INT,

  PRIMARY KEY (id)
);

drop type if exists item_type cascade;
create type item_type as enum (
  'POKE_BALL',
  'GREAT_BALL',
  'ULTRA_BALL',
  'MASTER_BALL',
  'STARTER_BALL',
  'BOND_BALL',
  'POTION',
  'SUPER_POTION',
  'HYPER_POTION',
  'MAX_POTION',
  'REVIVE'
);

drop table if exists items cascade;
create table items(
  id SERIAL,
  item_type item_type,
  qty INT,
  owner_id INT,

  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) REFERENCES users (id),
  UNIQUE (item_type, owner_id)
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

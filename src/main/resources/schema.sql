--CREATE TABLE IF NOT EXISTS users(
--  id SERIAL,
--  name VARCHAR,
--  money INT,
--
--  PRIMARY KEY (id)
--);

--CREATE TYPE IF NOT EXISTS item_type as enum (
--  'POKE_BALL',
--  'GREAT_BALL',
--  'ULTRA_BALL',
--  'MASTER_BALL',
--  'STARTER_BALL',
--  'BOND_BALL',
--  'POTION',
--  'SUPER_POTION',
--  'HYPER_POTION',
--  'MAX_POTION',
--  'REVIVE'
--);

--create table items(
--  id SERIAL,
--  item_type item_type,
--  qty INT,
--  owner_id INT,
--
--  PRIMARY KEY (id),
--  FOREIGN KEY (owner_id) REFERENCES users (id),
--  UNIQUE (item_type, owner_id)
--);

CREATE TABLE IF NOT EXISTS habitats(
  id SERIAL,
  habitat_type VARCHAR,
  rarity FLOAT,
  pokedex_id INTEGER,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pokemon(
  id SERIAL,
  pokedex_id INTEGER,
  hp INTEGER,
  exp INTEGER,
  bond_exp INTEGER,
  owner_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS spawns(
                     id SERIAL,
                     world_x DOUBLE PRECISION,
                     world_y DOUBLE PRECISION,
                     pokemon_id INTEGER,
                     start_timestamp BIGINT,
                     end_timestamp BIGINT,

                     PRIMARY KEY (id),
                     FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
);

CREATE TABLE IF NOT EXISTS visited_locations(
  id SERIAL,
  tile_x INTEGER,
  tile_y INTEGER,
  timestamp BIGINT,

  PRIMARY KEY (id),
  UNIQUE (tile_x, tile_y)
);

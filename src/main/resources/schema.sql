CREATE TABLE IF NOT EXISTS habitats(
  id SERIAL,
  habitat_type VARCHAR NOT NULL,
  rarity FLOAT NOT NULL,
  pokedex_id INTEGER NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pokemon(
  id SERIAL,
  pokedex_id INTEGER NOT NULL,
  hp INTEGER NOT NULL,
  exp INTEGER NOT NULL,
  bond_exp INTEGER NOT NULL,
  owner_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS spawns(
  id SERIAL,
  world_x DOUBLE PRECISION NOT NULL,
  world_y DOUBLE PRECISION NOT NULL,
  pokemon_id INTEGER NOT NULL,
  start_timestamp BIGINT NOT NULL,
  end_timestamp BIGINT NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
);

CREATE TABLE IF NOT EXISTS visited_locations(
  id SERIAL,
  tile_x INTEGER NOT NULL,
  tile_y INTEGER NOT NULL,
  timestamp BIGINT NOT NULL,

  PRIMARY KEY (id),
  UNIQUE (tile_x, tile_y)
);

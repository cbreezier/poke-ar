drop table if exists pokemon;
create table pokemon(
  id SERIAL,
  name VARCHAR,

  PRIMARY KEY (id)
);

drop table if exists habitats;
create table habitats(
  id SERIAL,
  habitat_type VARCHAR,
  rarity FLOAT,
  pokemon_id INTEGER,

  PRIMARY KEY (id),
  FOREIGN KEY (pokemon_id) REFERENCES pokemon (id)
);

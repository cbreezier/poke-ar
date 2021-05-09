create table items(
  id BIGSERIAL,
  item_type VARCHAR,
  qty INTEGER,
  owner_id BIGINT,

  PRIMARY KEY (id),
  FOREIGN KEY (owner_id) REFERENCES users (id),
  UNIQUE (item_type, owner_id)
);

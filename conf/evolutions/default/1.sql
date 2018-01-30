# --- !Ups

CREATE TABLE "products" (
  "id"         INTEGER PRIMARY KEY AUTOINCREMENT,
  "name"       TEXT,
  "sku"        TEXT,
  "size"       TEXT,
  "color"      TEXT,
  "product_id" TEXT
);
# --- !Downs
;
DROP TABLE "products";
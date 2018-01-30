# --- !Ups

CREATE TABLE `stocks` (
  `id`                INTEGER PRIMARY KEY AUTOINCREMENT,
  `product_id`        INTEGER,
  `quantity_hand`     INTEGER,
  `quantity_progress` INTEGER,
  `purchase_price`    INTEGER,
  `selling_price`     INTEGER,
  `receipt`           INTEGER,
  `note`              INTEGER,
  `created`           TEXT                DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES products (id)
);
# --- !Downs
;
DROP TABLE "stocks";
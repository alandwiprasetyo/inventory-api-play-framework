# --- !Ups
CREATE TABLE `orders` (
  `id`            INTEGER PRIMARY KEY AUTOINCREMENT,
  `product_id`    INTEGER,
  `stock_id`      INTEGER,
  `quantity`      INTEGER,
  `total`         INTEGER,
  `selling_price` INTEGER,
  `note`          INTEGER,
  `created`       TEXT                DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (stock_id) REFERENCES stocks (id)
);
# --- !Downs
;
DROP TABLE "orders";
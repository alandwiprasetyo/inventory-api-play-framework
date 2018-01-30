# --- !Ups
CREATE VIEW product_values AS
  SELECT
    p.id,
    p.sku,
    p.name,
    COALESCE((SELECT SUM(i.quantity_hand)
              FROM stocks i
              WHERE i.product_id = p.id) -
             (SELECT SUM(o.quantity)
              FROM orders o
              WHERE o.product_id = p.id), 0) AS available_stock,
    COALESCE((SELECT AVG(s.purchase_price)
              FROM stocks s
              WHERE s.product_id = p.id), 0) AS avg_purchase_price
  FROM products p
  GROUP BY p.id
  # --- !Downs
;
DROP VIEW "product_values";
# --- !Ups
CREATE VIEW selling_reports AS
  SELECT
    o.id,
    o.quantity,
    o.selling_price,
    s.purchase_price,
    p.name                               AS product_name,
    (o.selling_price * o.quantity)       AS total,
    (o.selling_price - s.purchase_price) AS profit,
    p.sku                                AS product_sku
  FROM orders o
    JOIN products p ON p.id = o.product_id
    JOIN stocks s ON s.id = o.stock_id
  # --- !Downs
;
DROP VIEW "selling_reports";
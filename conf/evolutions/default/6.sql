# --- !Ups
CREATE VIEW product_values AS SELECT p.id,p.sku, p.name,
(SELECT SUM(i.quantity_hand) FROM stocks i WHERE i.product_id = p.id ) -
 (SELECT SUM(o.quantity) FROM orders o WHERE o.product_id = p.id )AS available_stock, (SELECT AVG(s.purchase_price) FROM stocks s WHERE s.product_id = p.id ) as avg_purchase_price FROM products p GROUP BY p.id
  # --- !Downs
;
DROP VIEW "product_values";
-- ==================== INSERT REALISTIC PRODUCTS ====================
INSERT INTO products (sku, name, description, price, active)
VALUES
('PARLE-G-70G', 'Parle-G Biscuit 70g', 'Classic glucose biscuit from Parle', 10.00, TRUE),
('DAIRYMILK-45G', 'Cadbury Dairy Milk 45g', 'Milk chocolate bar', 45.00, TRUE),
('MAGGIE-70G', 'Maggie Masala Noodles 70g', 'Instant noodles pack', 15.00, TRUE),
('AMUL-MILK-1L', 'Amul Taaza Milk 1L', 'Fresh toned milk', 65.00, TRUE),
('DETTOL-100ML', 'Dettol Antiseptic 100ml', 'Multipurpose disinfectant liquid', 35.00, TRUE);

-- ==================== INSERT SINGLE MAIN WAREHOUSE ====================
INSERT INTO warehouses (name, address, latitude, longitude, type)
VALUES ('Main Warehouse', 'Delhi NCR', 28.6139, 77.2090, 'PRIMARY');

-- ==================== INSERT LOTS FOR REALISTIC STOCK ====================
-- Parle-G Lots
INSERT INTO lots (product_id, warehouse_id, expiry_date, available_quantity)
VALUES
(1, 1, '2026-05-20', 500),   -- fresh batch
(1, 1, '2026-03-15', 200);   -- earlier expiry (FEFO test)

-- Dairy Milk Lots
INSERT INTO lots (product_id, warehouse_id, expiry_date, available_quantity)
VALUES
(2, 1, '2026-08-30', 300),
(2, 1, '2026-07-10', 150);

-- Maggie Lots
INSERT INTO lots (product_id, warehouse_id, expiry_date, available_quantity)
VALUES
(3, 1, '2026-02-12', 400);

-- Amul Milk Lots
INSERT INTO lots (product_id, warehouse_id, expiry_date, available_quantity)
VALUES
(4, 1, '2026-12-05', 100),
(4, 1, '2026-12-02', 70);

-- Dettol Lots
INSERT INTO lots (product_id, warehouse_id, expiry_date, available_quantity)
VALUES
(5, 1, '2026-01-01', 250);

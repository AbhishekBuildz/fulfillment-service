-- ============= BASE MODEL SHARED FIELDS =============
-- BaseModel: id (BIGSERIAL), created_at, updated_at
-- We'll add created_at & updated_at to all tables.

-- ============= PRODUCTS TABLE =============
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE PRECISION NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============= WAREHOUSES TABLE =============
CREATE TABLE warehouses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    type VARCHAR(50),

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ============= LOTS TABLE =============
CREATE TABLE lots (
    id BIGSERIAL PRIMARY KEY,

    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,

    expiry_date DATE NOT NULL,
    available_quantity BIGINT NOT NULL,
    reserved_quantity BIGINT NOT NULL DEFAULT 0,

    version BIGINT DEFAULT 0,  -- optimistic locking

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_lots_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_lots_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);

-- Helpful indexes
CREATE INDEX idx_lots_product_id ON lots(product_id);
CREATE INDEX idx_lots_warehouse_id ON lots(warehouse_id);
CREATE INDEX idx_lots_expiry_date ON lots(expiry_date);

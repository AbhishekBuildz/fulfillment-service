CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,

    -- BaseModel timestamps
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    -- Foreign keys
    user_id BIGINT NOT NULL,

    -- Enum stored as string
    status VARCHAR(50) NOT NULL,

    CONSTRAINT fk_orders_user_id
        FOREIGN KEY (user_id) REFERENCES users(id)
);

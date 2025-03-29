CREATE TABLE inventories (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        product_id VARCHAR(50) UNIQUE NOT NULL,
                        sku VARCHAR(50) UNIQUE NOT NULL,
                        stock INT NOT NULL DEFAULT 0,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP NOT NULL
);

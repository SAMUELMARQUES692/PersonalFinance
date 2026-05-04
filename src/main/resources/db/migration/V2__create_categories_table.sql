CREATE TABLE categories (
                            id          BIGSERIAL PRIMARY KEY,
                            name        VARCHAR(255) NOT NULL,
                            description VARCHAR(255),
                            user_id     BIGINT NOT NULL REFERENCES users(id),
                            created_at  TIMESTAMP,
                            updated_at  TIMESTAMP
);
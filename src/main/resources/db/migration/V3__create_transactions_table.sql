CREATE TABLE transactions (
                              id          BIGSERIAL PRIMARY KEY,
                              description VARCHAR(255) NOT NULL,
                              amount      NUMERIC(19, 2) NOT NULL,
                              type        VARCHAR(50) NOT NULL,
                              date        DATE NOT NULL,
                              category_id BIGINT NOT NULL REFERENCES categories(id),
                              user_id     BIGINT NOT NULL REFERENCES users(id),
                              created_at  TIMESTAMP,
                              updated_at  TIMESTAMP
);
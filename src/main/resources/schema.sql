CREATE TABLE tutorial (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) UNIQUE,
    description VARCHAR(255),
    published BOOLEAN
)

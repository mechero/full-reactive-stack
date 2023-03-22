CREATE TABLE IF NOT EXISTS quote (
    id bigint PRIMARY KEY auto_increment,
    book VARCHAR(255),
    content TEXT
);
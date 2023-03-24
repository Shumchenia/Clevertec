CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    price DECIMAL NOT NULL,
    name  VARCHAR(60) UNIQUE
);
CREATE TABLE IF NOT EXISTS discount_card
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY,
    percent VARCHAR(64)   NOT NULL,
    code    varchar(10) UNIQUE NOT NULL
);
INSERT INTO product(price, name)
VALUES (100, 'meet'),
       (20, 'egg'),
       (30, 'bread');
INSERT INTO discount_card(percent, code)
VALUES (2, '111'),
       (30, '222'),
       (50, '333');

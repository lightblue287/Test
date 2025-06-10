CREATE TABLE IF NOT EXISTS products
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(100) NOT NULL,
    price  INT          NOT NULL,
    stock  INT          NOT NULL,
    status INT          NOT NULL DEFAULT 1 comment '0=下架, 1=上架'
);

CREATE TABLE IF NOT EXISTS orders
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    customer_name  VARCHAR(100) NOT NULL,
    product_detail TEXT         NOT NULL,
    total_price    INT          NOT NULL,
    status         INT          NOT NULL comment '0=失敗, 1=成功',
    created_time   DATETIME     NOT NULL
);
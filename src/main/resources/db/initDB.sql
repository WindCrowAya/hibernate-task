SET FOREIGN_KEY_CHECKS=0; # отменил проверку FK, поскольку без него не удаляется таблица `categories`, здесь ON DELETE CASCADE не помогает

DROP TABLE IF EXISTS `order_products`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `orders`;


CREATE TABLE `categories` (
  `category_id`                INT         NOT NULL, #AUTO_INCREMENT,
  `parent_category_id`         INT,
  `category_name`              VARCHAR(50) NOT NULL,
  PRIMARY KEY (`category_id`),
  FOREIGN KEY (`parent_category_id`) REFERENCES categories (`category_id`) ON DELETE CASCADE
);

CREATE TABLE `products` (
  `product_id`                 INT           NOT NULL, #AUTO_INCREMENT,
  `category_id`                INT           NOT NULL,
  `product_name`               VARCHAR(50)   NOT NULL,
  `description`                VARCHAR(200),
  `price`                      DECIMAL(10,4) NOT NULL,
  `quantity_in_warehouse`      INT           NOT NULL,
  PRIMARY KEY (`product_id`),
  FOREIGN KEY (`category_id`) REFERENCES categories (`category_id`) ON DELETE CASCADE
);

CREATE TABLE `orders` (
  `order_id`                   INT         NOT NULL AUTO_INCREMENT,
  `address`                    VARCHAR(50) NOT NULL,
  `payment_method`             VARCHAR(50) NOT NULL,
  `last_updated_date`          TIMESTAMP   NOT NULL,
  `order_status`               TINYINT(1)  NOT NULL,
  PRIMARY KEY (`order_id`)
);

CREATE TABLE `order_products` (
  `order_id`                   INT NOT NULL,
  `product_id`                 INT NOT NULL,
  `quantity_of_products`       INT NOT NULL,
  PRIMARY KEY (`order_id`, `product_id`),
  FOREIGN KEY (`order_id`)   REFERENCES orders (`order_id`)     ON DELETE CASCADE,
  FOREIGN KEY (`product_id`) REFERENCES products (`product_id`) ON DELETE CASCADE
);

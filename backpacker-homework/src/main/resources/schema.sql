CREATE TABLE `member` (
	`uid` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL COLLATE 'utf8mb4_general_ci',
	`password` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`email` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`created_at` DATETIME NOT NULL,
	PRIMARY KEY (`uid`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;

CREATE TABLE `orders` (
	`uid` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`order_number` VARCHAR(12) NOT NULL COLLATE 'utf8mb4_general_ci',
	`owner_uid` BIGINT(20) NOT NULL,
	`product_name` VARCHAR(100) NOT NULL COLLATE 'utf8mb4_general_ci',
	`created_at` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	PRIMARY KEY (`uid`) USING BTREE,
	UNIQUE INDEX `orderNumber` (`order_number`) USING BTREE,
	INDEX `MEMBER_ORDERS` (`owner_uid`) USING BTREE,
	CONSTRAINT `MEMBER_ORDERS` FOREIGN KEY (`owner_uid`) REFERENCES `backpacker`.`member` (`uid`) ON UPDATE CASCADE ON DELETE NO ACTION
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB;
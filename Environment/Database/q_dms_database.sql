SHOW DATABASES;
# DROP DATABASE `q_dms_db`;
# DROP TABLE `user_info`;
# DROP TABLE `log_record`;
# SELECT * FROM `log_record`;
CREATE DATABASE IF NOT EXISTS `q_dms_db`;

USE `q_dms_db`;

# 用户信息表 储存登录账户信息
CREATE TABLE IF NOT EXISTS `user_info`
(
    `username` VARCHAR(20) PRIMARY KEY,
    `password` VARCHAR(20),
    `sex`      VARCHAR(2)
);

# 用户表中添加假数据
INSERT INTO `user_info`
VALUES ("user1", "user1", "男");
INSERT INTO `user_info`
VALUES ("user2", "user2", "女");
INSERT INTO `user_info`
VALUES ("user3", "user3", "男");

# 日志记录表 储存日志信息
CREATE TABLE IF NOT EXISTS `log_record`
(
    `logId`            INT AUTO_INCREMENT PRIMARY KEY,
    `creationTime`     TIMESTAMP,
    `creationLocation` VARCHAR(50),
    `status`           VARCHAR(1),
    `logUsername`      VARCHAR(20),
    `ip`               VARCHAR(20),
    `logType`          VARCHAR(1),
    `recCreator`       VARCHAR(20),
    FOREIGN KEY (`recCreator`) REFERENCES `user_info` (`username`)
);

# 日志记录表中添加假数据
INSERT INTO `log_record`(`creationTime`, `creationLocation`, `status`, `logUsername`, `ip`, `logType`, `recCreator`)
VALUES ("2022-11-11 11:11:11", "成都", "1", "张三", "192.168.1.1", "0", "user1");
INSERT INTO `log_record`(`creationTime`, `creationLocation`, `status`, `logUsername`, `ip`, `logType`, `recCreator`)
VALUES ("2023-12-12 22:22:22", "成都", "2", "李四", "192.168.2.2", "1", "user2");
INSERT INTO `log_record`(`creationTime`, `creationLocation`, `status`, `logUsername`, `ip`, `logType`, `recCreator`)
VALUES ("2023-10-10 23:23:23", "成都", "2", "王五", "192.168.2.3", "0", "user3");


# 物流记录表 储存物流信息
CREATE TABLE IF NOT EXISTS `logistics_record`
(
    `logisticsId`   INT AUTO_INCREMENT PRIMARY KEY,
    `creationTime`  TIMESTAMP,
    `destination`   VARCHAR(50),
    `status`        VARCHAR(1),
    `handler`       VARCHAR(20),
    `consignee`     VARCHAR(20),
    `logisticsType` VARCHAR(1),
    `recCreator`    VARCHAR(20),
    FOREIGN KEY (`recCreator`) REFERENCES `user_info` (`username`)
);

# 向物流记录表中添加假数据
INSERT INTO `logistics_record`(`creationTime`, `destination`, `status`, `handler`, `consignee`, `logisticsType`,
                               `recCreator`)
VALUES ("2022-11-11 11:11:11", "成都", "1", "张三", "李四", "0", "user2");
INSERT INTO `logistics_record`(`creationTime`, `destination`, `status`, `handler`, `consignee`, `logisticsType`,
                               `recCreator`)
VALUES ("2022-12-12 22:22:22", "北京", "2", "王五", "马六", "1", "user1");
INSERT INTO `logistics_record`(`creationTime`, `destination`, `status`, `handler`, `consignee`, `logisticsType`,
                               `recCreator`)
VALUES ("2022-12-12 23:23:23", "纽约", "1", "李四", "张三", "2", "user3");







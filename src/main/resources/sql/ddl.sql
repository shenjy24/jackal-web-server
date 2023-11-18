CREATE DATABASE `web`;
CREATE TABLE `web`.`user`
(
    `user_id`     bigint NOT NULL COMMENT '用户id',
    `account`     varchar(255) DEFAULT NULL COMMENT '账户',
    `user_name`   varchar(255) DEFAULT NULL COMMENT '用户名称',
    `user_icon`   varchar(255) DEFAULT NULL COMMENT '头像',
    `password`    varchar(255) DEFAULT NULL COMMENT '密码',
    `user_status` int          DEFAULT NULL COMMENT '用户状态：0-删除 1-正常',
    `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

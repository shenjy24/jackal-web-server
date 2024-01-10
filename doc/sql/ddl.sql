CREATE DATABASE `web`;
CREATE TABLE `web`.`user`
(
    `user_id`     bigint NOT NULL COMMENT '用户id',
    `account`     varchar(255) DEFAULT NULL COMMENT '账户',
    `user_name`   varchar(255) DEFAULT NULL COMMENT '用户名称',
    `user_icon`   varchar(255) DEFAULT NULL COMMENT '头像',
    `password`    varchar(255) DEFAULT NULL COMMENT '密码',
    `user_status` int          DEFAULT NULL COMMENT '用户状态：0-删除 1-正常',
    `create_time` datetime(6)  DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime(6)  DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `web`.`user_token`
(
    `token_id`    bigint      NOT NULL COMMENT '逻辑主键',
    `user_id`     bigint      NOT NULL COMMENT '用户ID',
    `token`       varchar(64) NOT NULL COMMENT 'token值',
    `expire_time` datetime    NOT NULL COMMENT '过期时间',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`token_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户登录token表';

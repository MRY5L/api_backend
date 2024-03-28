# 数据库初始化

-- 创建库
create database if not exists api_db;

-- 切换库
use api_db;


-- 用户表
CREATE TABLE if not exists `user`
(
    `id`           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `userName`     varchar(256)          DEFAULT NULL COMMENT '用户昵称',
    `userAccount`  varchar(256) NOT NULL COMMENT '账号',
    `userAvatar`   varchar(1024)         DEFAULT NULL COMMENT '用户头像',
    `gender`       tinyint(4)            DEFAULT NULL COMMENT '性别',
    `userRole`     varchar(256) NOT NULL DEFAULT 'user' COMMENT '用户角色：user / admin',
    `userPassword` varchar(512) NOT NULL COMMENT '密码',
    `accessKey`    varchar(512) NOT NULL COMMENT 'accessKey',
    `secretKey`    varchar(512) NOT NULL COMMENT 'secretKey',
    `createTime`   datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `updateTime`   datetime     NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `isDelete`     tinyint(4)   NOT NULL DEFAULT 0 COMMENT '是否删除',
    `leftNum`      int(11)      NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
    `signDate`     datetime     NOT NULL DEFAULT current_timestamp() COMMENT '签到时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_userAccount` (`userAccount`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = utf8 COMMENT ='用户';

-- 接口信息
CREATE TABLE if not exists api_db.`interface_info`
(
    `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`           varchar(256) NOT NULL COMMENT '名称',
    `description`    varchar(256)          DEFAULT NULL COMMENT '描述',
    `restful`        varchar(512) NOT NULL COMMENT '请求路径',
    `requestHeader`  text                  DEFAULT NULL COMMENT '请求头',
    `responseHeader` text                  DEFAULT NULL COMMENT '响应头',
    `status`         int(11)      NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
    `method`         varchar(256) NOT NULL COMMENT '请求类型',
    `userId`         bigint(20)   NOT NULL COMMENT '创建人',
    `createTime`     datetime     NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `updateTime`     datetime     NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `isDelete`       tinyint(4)   NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
    `requestParams`  text                  DEFAULT NULL COMMENT '请求参数',
    `host`           varchar(255) NOT NULL COMMENT '请求地址',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8 COMMENT ='接口信息';

-- 用户调用接口关系表
CREATE TABLE if not exists api_db.`user_interface_info`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `userId`          bigint(20) NOT NULL COMMENT '调用用户 id',
    `interfaceInfoId` bigint(20) NOT NULL COMMENT '接口 id',
    `totalNum`        int(11)    NOT NULL DEFAULT 0 COMMENT '总调用次数',
    `status`          int(11)    NOT NULL DEFAULT 0 COMMENT '0-正常，1-禁用',
    `createTime`      datetime   NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `updateTime`      datetime   NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `isDelete`        tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8 COMMENT ='用户调用接口关系';


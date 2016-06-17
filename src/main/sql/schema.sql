-- 数据库初始化脚本
-- 创建数据库
CREATE DATABASE seckill;
USE seckill;

-- 创建秒杀库存表
CREATE TABLE 'seckill'(
'seckill_id' BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
'name' varchar(200) NOT NULL COMMENT '商品名称',
'number' INT NOT NULL COMMENT '库存数量',
'start_time' TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
'end_time'  TIMESTAMP  NOT NULL COMMENT '秒杀结束时间'
'create_time' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
PRIMARY KEY (seckill_id),
key idx_create_time(create_time),
key idx_end_time(end_time),
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'

-- 初始化数据
INSERT INTO 'seckill'(name,number,start_time,end_time)
VALUES ('1000元秒杀Iphone6s',1000,'2016-06-13 00:00:00','2016-06-15 00:00:00')
VALUES ('800元秒杀iPad',500,'2016-06-13 00:00:00','2016-06-15 00:00:00')
VALUES ('500元秒杀小米4',400,'2016-06-13 00:00:00','2016-06-15 00:00:00')
VALUES ('200元秒杀Mate8',800,'2016-06-13 00:00:00','2016-06-15 00:00:00')

-- 创建秒杀明细表
CREATE TABLE 'success_killed'(
'seckill_id' BIGINT NOT NULL COMMENT '秒杀商品id',
'user_phone' bigint not null comment '用户手机号',
'state' tinyint not null default -1 comment '状态标识:-1无效 0成功 1已付款'
'create_time' TIMESTAMP not null comment '创建时间',
PRIMARY KEY (seckill_id,user_phone),
key idx_create_time(create_time)
)engine=InnoDB default charset=utf8 comment='秒杀明细表'
create table shard
(
   id                   bigint not null comment '主键',                     
   name                 varchar(20) not null comment '分片名称',
   hash_value           varchar(20) not null comment '散列值',
   group_id             bigint not null comment '所属分组', 
   writable             tinyint not null comment '是否可写[0:不可写，1：可写]', 
   primary key (id)
)engine=innodb default charset=utf8;
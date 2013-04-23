create table shard_group
(
   id                   bigint not null comment '主键',                     
   name                 varchar(20) not null comment '分组名称',
   state                tinyint(1) not null comment '状态',
   start_id             bigint not null comment 'id范围', 
   end_id               bigint not null comment 'id范围', 
   
   primary key (id)
)engine=innodb default charset=utf8;
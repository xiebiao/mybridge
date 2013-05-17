create table node
(
   id                   bigint not null comment '主键',                     
   ip                   varchar(20) not null comment 'ip',
   port                 bigint not null comment '端口',
   shard_id             bigint not null comment '所属分片', 
   group_id             bigint not null comment '所属分组',
   writable             tinyint not null comment '是否可写[0:不可写，1：可写]', 
   available            tinyint not null comment '是否可用[0:不可用，1：可用]', 
   create_time          datetime not null comment '创建时间',
   update_time          datetime not null comment '修改时间',
   primary key (id)
)engine=innodb default charset=utf8;
alter table node add unique(ip,port);
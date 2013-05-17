create table shard_group
(
   id                   bigint not null comment '主键',                     
   group_name                 varchar(20) not null comment '分组名称',
   writable             tinyint(1) not null comment '状态',
   start_id             bigint not null comment '数据范围', 
   end_id               bigint not null comment '数据范围',  
   
   create_time          datetime not null comment '创建时间',
   update_time          datetime not null comment '修改时间',   
   primary key (id)
)engine=innodb default charset=utf8;
alter table shard_group add unique(group_name);
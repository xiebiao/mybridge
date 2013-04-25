create table fragment_table
(
   id                   bigint not null comment '主键',  
   name                 varchar(20) not null comment '表名',
   shard_id             bigint not null comment '分片id',    
   start_id             bigint not null comment 'id范围', 
   end_id               bigint not null comment 'id范围',    
   primary key (id)
)engine=innodb default charset=utf8;
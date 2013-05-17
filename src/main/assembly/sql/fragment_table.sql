create table fragment_table
(
   id                   bigint not null comment '主键',  
   table_name           varchar(20) not null comment '表名',
   shard_id             bigint not null comment '分片id',    
   start_id             bigint not null comment 'id范围', 
   end_id               bigint not null comment 'id范围',   
   create_time          datetime not null comment '创建时间',
   update_time          datetime not null comment '修改时间',   
   primary key (id)
)engine=innodb default charset=utf8;
alter table fragment_table add unique(table_name);
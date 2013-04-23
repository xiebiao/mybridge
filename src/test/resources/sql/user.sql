create table user
(
   id                   bigint not null comment '主键',                     
   name                 varchar(20) not null comment '用户名',
   
   primary key (id)
)engine=innodb default charset=utf8;
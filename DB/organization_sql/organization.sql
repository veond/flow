/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/11/26 17:06:01                          */
/*==============================================================*/

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
  `id` varchar(36) NOT NULL,
   name                 varchar(50) NOT NULL DEFAULT '',
   parent_id            varchar(36) NOT NULL,
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   remark               varchar(200) NOT NULL DEFAULT '',
   primary key (id)
);

alter table department comment '��֯���ű�';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
  `id` varchar(36) NOT NULL,
   name                 varchar(50) NOT NULL DEFAULT '',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   remark               varchar(200) NOT NULL DEFAULT '',
   primary key (id)
);

alter table role comment '��֯����> ��ɫ';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
  `id` varchar(36) NOT NULL,
   dept_id              varchar(36) NOT NULL DEFAULT '',
   role_id              varchar(36) NOT NULL DEFAULT '',
   username             varchar(50) NOT NULL DEFAULT '',
   loginname            varchar(50) NOT NULL DEFAULT '',
   userpass             varchar(50) NOT NULL DEFAULT '',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   remark               varchar(200) NOT NULL DEFAULT '',
   `status` int(2) NOT NULL DEFAULT '1',
   primary key (id)
);

alter table user comment '��֯����> �û�';



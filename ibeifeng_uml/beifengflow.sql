/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2012-10-28 8:35:21                           */
/*==============================================================*/


alter table FLOWUSER
   drop constraint FK_FLOWUSER_RELATIONS_DEPARTME;

alter table FLOWUSER
   drop constraint FK_FLOWUSER_RELATIONS_FLOWROLE;

alter table HAVETODOTABLE
   drop constraint FK_HAVETODO_RELATIONS_WORKITEM;

alter table TODOTABLE
   drop constraint FK_TODOTABL_RELATIONS_WORKITEM;

alter table WORKITEM
   drop constraint FK_WORKITEM_RELATIONS_FLOWMODE;

drop table DEPARTMENT cascade constraints;

drop table FLOWMODEL cascade constraints;

drop table FLOWROLE cascade constraints;

drop index "Relationship_2_FK";

drop index "Relationship_1_FK";

drop table FLOWUSER cascade constraints;

drop index "Relationship_4_FK";

drop table HAVETODOTABLE cascade constraints;

drop index "Relationship_3_FK";

drop table TODOTABLE cascade constraints;

drop index "Relationship_5_FK";

drop table WORKITEM cascade constraints;

/*==============================================================*/
/* Table: DEPARTMENT                                            */
/*==============================================================*/
create table DEPARTMENT  (
   DEPARTMENTID         CHAR(36)                        not null,
   DEPARTMENTNAME       VARCHAR2(30),
   PARENTID             VARCHAR2(36),
   CREATETIME           DATE,
   constraint PK_DEPARTMENT primary key (DEPARTMENTID)
);

/*==============================================================*/
/* Table: FLOWMODEL                                             */
/*==============================================================*/
create table FLOWMODEL  (
   FLOWMODELID          CHAR(36)                        not null,
   FLOWMODELNAME        VARCHAR2(30),
   FLOWMODELFILE        VARCHAR2(100),
   constraint PK_FLOWMODEL primary key (FLOWMODELID)
);

/*==============================================================*/
/* Table: FLOWROLE                                              */
/*==============================================================*/
create table FLOWROLE  (
   FLOWROLEID           CHAR(36)                        not null,
   FLOWROLENAME         VARCHAR2(20),
   FLOWROLEREMARK       VARCHAR2(100),
   constraint PK_FLOWROLE primary key (FLOWROLEID)
);

/*==============================================================*/
/* Table: FLOWUSER                                              */
/*==============================================================*/
create table FLOWUSER  (
   FLOWUSERID           CHAR(36)                        not null,
   DEPARTMENTID         CHAR(36),
   FLOWROLEID           CHAR(36),
   FLOWUSERNAME         VARCHAR2(20),
   FLOWLOGINNAME        VARCHAR2(30),
   FLOWUSERPASSWORD     VARCHAR2(36),
   constraint PK_FLOWUSER primary key (FLOWUSERID)
);

/*==============================================================*/
/* Index: "Relationship_1_FK"                                   */
/*==============================================================*/
create index "Relationship_1_FK" on FLOWUSER (
   DEPARTMENTID ASC
);

/*==============================================================*/
/* Index: "Relationship_2_FK"                                   */
/*==============================================================*/
create index "Relationship_2_FK" on FLOWUSER (
   FLOWROLEID ASC
);

/*==============================================================*/
/* Table: HAVETODOTABLE                                         */
/*==============================================================*/
create table HAVETODOTABLE  (
   ID2                  CHAR(36)                        not null,
   WORKITEMID           CHAR(36),
   USERID               VARCHAR2(36),
   MODULENAME           VARCHAR2(30),
   WORKITEMNAME         VARCHAR2(30),
   PROCESSINSID         VARCHAR2(36),
   TODOSTATE            INTEGER,
   PROCESSTIME          DATE,
   constraint PK_HAVETODOTABLE primary key (ID2)
);

/*==============================================================*/
/* Index: "Relationship_4_FK"                                   */
/*==============================================================*/
create index "Relationship_4_FK" on HAVETODOTABLE (
   WORKITEMID ASC
);

/*==============================================================*/
/* Table: TODOTABLE                                             */
/*==============================================================*/
create table TODOTABLE  (
   ID                   CHAR(36)                        not null,
   WORKITEMID           CHAR(36),
   USERID               VARCHAR2(36),
   MODULENAME           VARCHAR2(30),
   WORKITEMNAME         VARCHAR2(30),
   PROCESSINSID         VARCHAR2(36),
   TODOSTATE            INTEGER,
   constraint PK_TODOTABLE primary key (ID)
);

/*==============================================================*/
/* Index: "Relationship_3_FK"                                   */
/*==============================================================*/
create index "Relationship_3_FK" on TODOTABLE (
   WORKITEMID ASC
);

/*==============================================================*/
/* Table: WORKITEM                                              */
/*==============================================================*/
create table WORKITEM  (
   WORKITEMID           CHAR(36)                        not null,
   FLOWMODELID          CHAR(36),
   WORKITEMNAME         VARCHAR2(30),
   WORKITEMTYPE         INTEGER,
   FROMID               VARCHAR2(380),
   TOID                 VARCHAR2(380),
   CONDITION            VARCHAR2(100),
   WORKITEMUSERID       VARCHAR2(36),
   constraint PK_WORKITEM primary key (WORKITEMID)
);

/*==============================================================*/
/* Index: "Relationship_5_FK"                                   */
/*==============================================================*/
create index "Relationship_5_FK" on WORKITEM (
   FLOWMODELID ASC
);


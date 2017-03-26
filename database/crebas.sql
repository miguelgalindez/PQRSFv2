/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     18/03/2017 4:40:36 p.m.                      */
/*==============================================================*/


alter table MUNICIPIO
   drop constraint FK_MUNCIP_PERTENECE_DEPARTAM;

alter table FUNCIONARIO
   drop constraint FK_FUNCIONA_PERTENECE_DEPENDEN;

alter table ORDEN
   drop constraint FK_ORDEN_ASIGNA_USUARIO;

alter table ORDEN
   drop constraint FK_ORDEN_ATIENDE_FUNCIONA;

alter table ORDEN
   drop constraint FK_ORDEN_GENERA_PQRSF;

alter table PERSONA
   drop constraint FK_PERSONA_HABITA_MUNICIPIO;

alter table PERSONA
   drop constraint FK_PERSONA_TIENETIPO_TIPOPERS;

alter table PERSONA
   drop constraint FK_PERSONA_TIENETIPO_TIPOIDEN;

alter table PQRSF
   drop constraint FK_PQRSF_EMANA_PERSONA;

alter table PQRSF
   drop constraint FK_PQRSF_TIENE_RADICADO;

alter table PQRSF
   drop constraint FK_PQRSF_TIENEMEDI_MEDIOREC;

alter table PQRSF
   drop constraint FK_PQRSF_TIENETIPO_TIPOPQRS;

alter table RADICADO
   drop constraint FK_RADICADO_REGISTRA_USUARIO;

alter table RADICADO
   drop constraint FK_RADICADO_TIENE2_PQRSF;

drop index PERTENECEA_FK;

drop table MUNICIPIO cascade constraints;

drop table DEPARTAMENTO cascade constraints;

drop table DEPENDENCIA cascade constraints;

drop index PERTENECE_A_FK;

drop table FUNCIONARIO cascade constraints;

drop table MEDIORECEPCION cascade constraints;

drop index ASIGNA_FK;

drop index GENERA_FK;

drop index ATIENDE_FK;

drop table ORDEN cascade constraints;

drop index TIENETIPOIDENTIFICACION_FK;

drop index TIENETIPO_FK;

drop index HABITA_FK;

drop table PERSONA cascade constraints;

drop index TIENEMEDIOR_FK;

drop index TIENETIPOPQRSF_FK;

drop index TIENE_FK;

drop index EMANA_FK;

drop table PQRSF cascade constraints;

drop index TIENE2_FK;

drop index REGISTRA_FK;

drop table RADICADO cascade constraints;

drop table TIPOIDENTIFICACION cascade constraints;

drop table TIPOPERSONA cascade constraints;

drop table TIPOPQRSF cascade constraints;

drop table USUARIO cascade constraints;

/*==============================================================*/
/* Table: MUNICIPIO                                                */
/*==============================================================*/
create table MUNICIPIO 
(
   MUNID                NUMBER(4)            not null,
   DEPTOID              NUMBER(2)            not null,
   MUNNOMBRE            VARCHAR2(64)         not null,
   constraint PK_MUNICIPIO primary key (MUNID)
);

/*==============================================================*/
/* Index: PERTENECEA_FK                                         */
/*==============================================================*/
create index PERTENECEA_FK on MUNICIPIO (
   DEPTOID ASC
);

/*==============================================================*/
/* Table: DEPARTAMENTO                                          */
/*==============================================================*/
create table DEPARTAMENTO 
(
   DEPTOID              NUMBER(2)            not null,
   DEPTONOMBRE          VARCHAR2(64)         not null,
   constraint PK_DEPARTAMENTO primary key (DEPTOID)
);

/*==============================================================*/
/* Table: DEPENDENCIA                                           */
/*==============================================================*/
create table DEPENDENCIA 
(
   DEPID                INTEGER              not null,
   DEPNOMBRE            VARCHAR2(256)        not null,
   constraint PK_DEPENDENCIA primary key (DEPID)
);

/*==============================================================*/
/* Table: FUNCIONARIO                                           */
/*==============================================================*/
create table FUNCIONARIO 
(
   FUNIDENTIFICACION    VARCHAR2(32)         not null,
   DEPID                INTEGER              not null,
   FUNCORREO            VARCHAR2(64)         not null,
   FUNNOMBRE            VARCHAR2(256)        not null,
   FUNTELEFONO          VARCHAR2(32),
   constraint PK_FUNCIONARIO primary key (FUNIDENTIFICACION)
);

/*==============================================================*/
/* Index: PERTENECE_A_FK                                        */
/*==============================================================*/
create index PERTENECE_A_FK on FUNCIONARIO (
   DEPID ASC
);

/*==============================================================*/
/* Table: MEDIORECEPCION                                        */
/*==============================================================*/
create table MEDIORECEPCION 
(
   MEDID                NUMBER(1)            not null,
   MEDDESCRIPCION         VARCHAR2(64)         not null,
   constraint PK_MEDIORECEPCION primary key (MEDID)
);

/*==============================================================*/
/* Table: ORDEN                                                 */
/*==============================================================*/
create table ORDEN 
(
   ORDID                INTEGER              not null,
   USUUSUARIO           VARCHAR2(64)         not null,
   PQRSFCODIGO          VARCHAR2(8)          not null,
   FUNIDENTIFICACION    VARCHAR2(32)         not null,
   ORDFECHAASIGNACION   DATE                 not null,
   ORDESTADO            NUMBER(1)            default 0 not null
      constraint CKC_ORDESTADO_ORDEN check (ORDESTADO in (0,1,2)),
   ORDFECHACIERRE       DATE,
   constraint PK_ORDEN primary key (ORDID)
);

/*==============================================================*/
/* Index: ATIENDE_FK                                            */
/*==============================================================*/
create index ATIENDE_FK on ORDEN (
   FUNIDENTIFICACION ASC
);

/*==============================================================*/
/* Index: GENERA_FK                                             */
/*==============================================================*/
create index GENERA_FK on ORDEN (
   PQRSFCODIGO ASC
);

/*==============================================================*/
/* Index: ASIGNA_FK                                             */
/*==============================================================*/
create index ASIGNA_FK on ORDEN (
   USUUSUARIO ASC
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA 
(
   PERIDENTIFICACION    VARCHAR2(32)         not null,
   TIPIDEID             NUMBER(2)            not null,
   MUNID                NUMBER(4)            not null,
   TIPPERID             NUMBER(2)            not null,
   PERNOMBRES           VARCHAR2(64)         not null,
   PERAPELLIDOS         VARCHAR2(64)         not null,
   PERCORREO            VARCHAR2(64)         not null,   
   PERDIRECCION         VARCHAR2(128)        not null,
   PERTELEFONO          VARCHAR2(32),
   PERCELULAR           VARCHAR2(16)         not null,
   constraint PK_PERSONA primary key (PERIDENTIFICACION)
);

/*==============================================================*/
/* Index: HABITA_FK                                             */
/*==============================================================*/
create index HABITA_FK on PERSONA (
   MUNID ASC
);

/*==============================================================*/
/* Index: TIENETIPO_FK                                          */
/*==============================================================*/
create index TIENETIPO_FK on PERSONA (
   TIPPERID ASC
);

/*==============================================================*/
/* Index: TIENETIPOIDENTIFICACION_FK                            */
/*==============================================================*/
create index TIENETIPOIDENTIFICACION_FK on PERSONA (
   TIPIDEID ASC
);

/*==============================================================*/
/* Table: PQRSF                                                 */
/*==============================================================*/
create table PQRSF 
(
   PQRSFCODIGO          VARCHAR2(8)          not null,
   PERIDENTIFICACION    VARCHAR2(32)         not null,
   MEDID                NUMBER(1)            not null,
   RADID                VARCHAR2(32),
   TIPPQRSFID           NUMBER(1)            not null,
   PQRSFASUNTO          VARCHAR2(256)        not null,
   PQRSFDESCRIPCION     VARCHAR2(1024)       not null,   
   PQRSFESTADO          NUMBER(1)            default 0 not null
      constraint CKC_PQRSFESTADO_PQRSF check (PQRSFESTADO in (0,1,2)),
   PQRSFFECHACREACION   DATE                 not null,
   PQRSFFECHAVENCIMIENTO DATE,
   PQRSFFECHACIERRE     DATE,
   constraint PK_PQRSF primary key (PQRSFCODIGO)
);

/*==============================================================*/
/* Index: EMANA_FK                                              */
/*==============================================================*/
create index EMANA_FK on PQRSF (
   PERIDENTIFICACION ASC
);

/*==============================================================*/
/* Index: TIENE_FK                                              */
/*==============================================================*/
create index TIENE_FK on PQRSF (
   RADID ASC
);

/*==============================================================*/
/* Index: TIENETIPOPQRSF_FK                                     */
/*==============================================================*/
create index TIENETIPOPQRSF_FK on PQRSF (
   TIPPQRSFID ASC
);

/*==============================================================*/
/* Index: TIENEMEDIOR_FK                                        */
/*==============================================================*/
create index TIENEMEDIOR_FK on PQRSF (
   MEDID ASC
);

/*==============================================================*/
/* Table: RADICADO                                              */
/*==============================================================*/
create table RADICADO 
(
   RADID                VARCHAR2(32)         not null,
   PQRSFCODIGO          VARCHAR2(8)          not null,
   USUUSUARIO           VARCHAR2(64)         not null,
   RADFECHA             DATE                 not null,
   constraint PK_RADICADO primary key (RADID)
);

/*==============================================================*/
/* Index: REGISTRA_FK                                           */
/*==============================================================*/
create index REGISTRA_FK on RADICADO (
   USUUSUARIO ASC
);

/*==============================================================*/
/* Index: TIENE2_FK                                             */
/*==============================================================*/
create index TIENE2_FK on RADICADO (
   PQRSFCODIGO ASC
);

/*==============================================================*/
/* Table: TIPOIDENTIFICACION                                    */
/*==============================================================*/
create table TIPOIDENTIFICACION 
(
   TIPIDEID             NUMBER(2)            not null,
   TIPIDEDESCRIPCION    VARCHAR2(64)         not null,
   constraint PK_TIPOIDENTIFICACION primary key (TIPIDEID)
);

/*==============================================================*/
/* Table: TIPOPERSONA                                           */
/*==============================================================*/
create table TIPOPERSONA 
(
   TIPPERID             NUMBER(2)            not null,
   TIPPERDESCRIPCION    VARCHAR2(64)         not null,
   constraint PK_TIPOPERSONA primary key (TIPPERID)
);

/*==============================================================*/
/* Table: TIPOPQRSF                                             */
/*==============================================================*/
create table TIPOPQRSF 
(
   TIPPQRSFID           NUMBER(1)            not null,
   TIPPQRSFDESCRIPCION  VARCHAR2(16)         not null,
   constraint PK_TIPOPQRSF primary key (TIPPQRSFID)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO 
(
   USUUSUARIO           VARCHAR2(64)         not null,
   USUCONTRASENA        VARCHAR2(256)        not null,
   USUNOMBRE            VARCHAR2(256)        not null,
   USUROL               VARCHAR2(16)         not null
      constraint CKC_USUROL_USUARIO check (USUROL in ('SUPERADMIN','ADMIN','JUDICANTE')),
   USUFECHAINICIO       DATE                 not null,
   USUFECHAFIN          DATE,
   constraint PK_USUARIO primary key (USUUSUARIO)
);

alter table MUNICIPIO
   add constraint FK_MUNCIP_PERTENECE_DEPARTAM foreign key (DEPTOID)
      references DEPARTAMENTO (DEPTOID);

alter table FUNCIONARIO
   add constraint FK_FUNCIONA_PERTENECE_DEPENDEN foreign key (DEPID)
      references DEPENDENCIA (DEPID);

alter table ORDEN
   add constraint FK_ORDEN_ASIGNA_USUARIO foreign key (USUUSUARIO)
      references USUARIO (USUUSUARIO);

alter table ORDEN
   add constraint FK_ORDEN_ATIENDE_FUNCIONA foreign key (FUNIDENTIFICACION)
      references FUNCIONARIO (FUNIDENTIFICACION);

alter table ORDEN
   add constraint FK_ORDEN_GENERA_PQRSF foreign key (PQRSFCODIGO)
      references PQRSF (PQRSFCODIGO);

alter table PERSONA
   add constraint FK_PERSONA_HABITA_MUNICIPIO foreign key (MUNID)
      references MUNICIPIO (MUNID);

alter table PERSONA
   add constraint FK_PERSONA_TIENETIPO_TIPOPERS foreign key (TIPPERID)
      references TIPOPERSONA (TIPPERID);

alter table PERSONA
   add constraint FK_PERSONA_TIENETIPO_TIPOIDEN foreign key (TIPIDEID)
      references TIPOIDENTIFICACION (TIPIDEID);

alter table PQRSF
   add constraint FK_PQRSF_EMANA_PERSONA foreign key (PERIDENTIFICACION)
      references PERSONA (PERIDENTIFICACION);

alter table PQRSF
   add constraint FK_PQRSF_TIENE_RADICADO foreign key (RADID)
      references RADICADO (RADID);

alter table PQRSF
   add constraint FK_PQRSF_TIENEMEDI_MEDIOREC foreign key (MEDID)
      references MEDIORECEPCION (MEDID);

alter table PQRSF
   add constraint FK_PQRSF_TIENETIPO_TIPOPQRS foreign key (TIPPQRSFID)
      references TIPOPQRSF (TIPPQRSFID);

alter table RADICADO
   add constraint FK_RADICADO_REGISTRA_USUARIO foreign key (USUUSUARIO)
      references USUARIO (USUUSUARIO);

alter table RADICADO
   add constraint FK_RADICADO_TIENE2_PQRSF foreign key (PQRSFCODIGO)
      references PQRSF (PQRSFCODIGO);


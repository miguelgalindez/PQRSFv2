/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     18/03/2017 3:47:18 p.m.                      */
/*==============================================================*/



-- Type package declaration
create or replace package PDTypes  
as
    TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/


drop trigger COMPOUNDDELETETRIGGER_CIUDAD
/

drop trigger COMPOUNDINSERTTRIGGER_CIUDAD
/

drop trigger COMPOUNDUPDATETRIGGER_CIUDAD
/

drop trigger TIB_CIUDAD
/

drop trigger COMPOUNDDELETETRIGGER_DEPARTAM
/

drop trigger COMPOUNDINSERTTRIGGER_DEPARTAM
/

drop trigger COMPOUNDUPDATETRIGGER_DEPARTAM
/

drop trigger TIB_DEPARTAMENTO
/

drop trigger COMPOUNDDELETETRIGGER_DEPENDEN
/

drop trigger COMPOUNDINSERTTRIGGER_DEPENDEN
/

drop trigger COMPOUNDUPDATETRIGGER_DEPENDEN
/

drop trigger TIB_DEPENDENCIA
/

drop trigger COMPOUNDDELETETRIGGER_MEDIOREC
/

drop trigger COMPOUNDINSERTTRIGGER_MEDIOREC
/

drop trigger COMPOUNDUPDATETRIGGER_MEDIOREC
/

drop trigger TIB_MEDIORECEPCION
/

drop trigger COMPOUNDDELETETRIGGER_ORDEN
/

drop trigger COMPOUNDINSERTTRIGGER_ORDEN
/

drop trigger COMPOUNDUPDATETRIGGER_ORDEN
/

drop trigger TIB_ORDEN
/

drop trigger COMPOUNDDELETETRIGGER_TIPOIDEN
/

drop trigger COMPOUNDINSERTTRIGGER_TIPOIDEN
/

drop trigger COMPOUNDUPDATETRIGGER_TIPOIDEN
/

drop trigger TIB_TIPOIDENTIFICACION
/

drop trigger COMPOUNDDELETETRIGGER_TIPOPERS
/

drop trigger COMPOUNDINSERTTRIGGER_TIPOPERS
/

drop trigger COMPOUNDUPDATETRIGGER_TIPOPERS
/

drop trigger TIB_TIPOPERSONA
/

drop trigger COMPOUNDDELETETRIGGER_TIPOPQRS
/

drop trigger COMPOUNDINSERTTRIGGER_TIPOPQRS
/

drop trigger COMPOUNDUPDATETRIGGER_TIPOPQRS
/

drop trigger TIB_TIPOPQRSF
/

alter table CIUDAD
   drop constraint FK_CIUDAD_PERTENECE_DEPARTAM
/

alter table FUNCIONARIO
   drop constraint FK_FUNCIONA_PERTENECE_DEPENDEN
/

alter table ORDEN
   drop constraint FK_ORDEN_ASIGNA_USUARIO
/

alter table ORDEN
   drop constraint FK_ORDEN_ATIENDE_FUNCIONA
/

alter table ORDEN
   drop constraint FK_ORDEN_GENERA_PQRSF
/

alter table PERSONA
   drop constraint FK_PERSONA_HABITA_CIUDAD
/

alter table PERSONA
   drop constraint FK_PERSONA_TIENETIPO_TIPOPERS
/

alter table PERSONA
   drop constraint FK_PERSONA_TIENETIPO_TIPOIDEN
/

alter table PQRSF
   drop constraint FK_PQRSF_EMANA_PERSONA
/

alter table PQRSF
   drop constraint FK_PQRSF_TIENE_RADICADO
/

alter table PQRSF
   drop constraint FK_PQRSF_TIENEMEDI_MEDIOREC
/

alter table PQRSF
   drop constraint FK_PQRSF_TIENETIPO_TIPOPQRS
/

alter table RADICADO
   drop constraint FK_RADICADO_REGISTRA_USUARIO
/

alter table RADICADO
   drop constraint FK_RADICADO_TIENE2_PQRSF
/

drop index PERTENECEA_FK
/

drop table CIUDAD cascade constraints
/

drop table DEPARTAMENTO cascade constraints
/

drop table DEPENDENCIA cascade constraints
/

drop index PERTENECE_A_FK
/

drop table FUNCIONARIO cascade constraints
/

drop table MEDIORECEPCION cascade constraints
/

drop index ASIGNA_FK
/

drop index GENERA_FK
/

drop index ATIENDE_FK
/

drop table ORDEN cascade constraints
/

drop index TIENETIPOIDENTIFICACION_FK
/

drop index TIENETIPO_FK
/

drop index HABITA_FK
/

drop table PERSONA cascade constraints
/

drop index TIENEMEDIOR_FK
/

drop index TIENETIPOPQRSF_FK
/

drop index TIENE_FK
/

drop index EMANA_FK
/

drop table PQRSF cascade constraints
/

drop index TIENE2_FK
/

drop index REGISTRA_FK
/

drop table RADICADO cascade constraints
/

drop table TIPOIDENTIFICACION cascade constraints
/

drop table TIPOPERSONA cascade constraints
/

drop table TIPOPQRSF cascade constraints
/

drop table USUARIO cascade constraints
/

drop sequence CIUIDSEQUENCE
/

drop sequence DEPIDSEQUENCE
/

drop sequence DEPTOIDSEQUENCE
/

drop sequence MEDIDSEQUENCE
/

drop sequence ORDIDSEQUENCE
/

drop sequence TIPIDEIDSEQUENCE
/

drop sequence TIPPERIDSEQUENCE
/

drop sequence TIPPQRSFIDSEQUENCE
/

create sequence CIUIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence DEPIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence DEPTOIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence MEDIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence ORDIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence TIPIDEIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence TIPPERIDSEQUENCE
increment by 1
start with 1
nocycle
/

create sequence TIPPQRSFIDSEQUENCE
increment by 1
start with 1
nocycle
/

/*==============================================================*/
/* Table: CIUDAD                                                */
/*==============================================================*/
create table CIUDAD 
(
   CIUID                NUMBER(4)            not null,
   DEPTOID              NUMBER(2)            not null,
   CIUNOMBRE            VARCHAR2(64)         not null,
   constraint PK_CIUDAD primary key (CIUID)
)
/

/*==============================================================*/
/* Index: PERTENECEA_FK                                         */
/*==============================================================*/
create index PERTENECEA_FK on CIUDAD (
   DEPTOID ASC
)
/

/*==============================================================*/
/* Table: DEPARTAMENTO                                          */
/*==============================================================*/
create table DEPARTAMENTO 
(
   DEPTOID              NUMBER(2)            not null,
   DEPTONOMBRE          VARCHAR2(64)         not null,
   constraint PK_DEPARTAMENTO primary key (DEPTOID)
)
/

/*==============================================================*/
/* Table: DEPENDENCIA                                           */
/*==============================================================*/
create table DEPENDENCIA 
(
   DEPID                INTEGER              not null,
   DEPNOMBRE            VARCHAR2(256)        not null,
   constraint PK_DEPENDENCIA primary key (DEPID)
)
/

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
)
/

/*==============================================================*/
/* Index: PERTENECE_A_FK                                        */
/*==============================================================*/
create index PERTENECE_A_FK on FUNCIONARIO (
   DEPID ASC
)
/

/*==============================================================*/
/* Table: MEDIORECEPCION                                        */
/*==============================================================*/
create table MEDIORECEPCION 
(
   MEDID                NUMBER(1)            not null,
   MEDRECEPCION         VARCHAR2(64)         not null,
   constraint PK_MEDIORECEPCION primary key (MEDID)
)
/

/*==============================================================*/
/* Table: ORDEN                                                 */
/*==============================================================*/
create table ORDEN 
(
   ORDID                INTEGER              not null,
   USUUSUARIO           VARCHAR2(64)         not null,
   PQSRFCODIGO          VARCHAR2(8)          not null,
   FUNIDENTIFICACION    VARCHAR2(32)         not null,
   ORDFECHAASIGNACION   DATE                 not null,
   ORDESTADO            NUMBER(1)            default 0 not null
      constraint CKC_ORDESTADO_ORDEN check (ORDESTADO in (0,1,2)),
   ORDFECHACIERRE       DATE,
   constraint PK_ORDEN primary key (ORDID)
)
/

/*==============================================================*/
/* Index: ATIENDE_FK                                            */
/*==============================================================*/
create index ATIENDE_FK on ORDEN (
   FUNIDENTIFICACION ASC
)
/

/*==============================================================*/
/* Index: GENERA_FK                                             */
/*==============================================================*/
create index GENERA_FK on ORDEN (
   PQSRFCODIGO ASC
)
/

/*==============================================================*/
/* Index: ASIGNA_FK                                             */
/*==============================================================*/
create index ASIGNA_FK on ORDEN (
   USUUSUARIO ASC
)
/

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA 
(
   PERIDENTIFICACION    VARCHAR2(32)         not null,
   TIPIDEID             NUMBER(2)            not null,
   CIUID                NUMBER(4)            not null,
   TIPPERID             NUMBER(2)            not null,
   PERNOMBRES           VARCHAR2(64)         not null,
   PERAPELLIDOS         VARCHAR2(64)         not null,
   PERCORREO            VARCHAR2(64)         not null,
   USUCIUDAD            INTEGER              not null,
   PERDIRECCION         VARCHAR2(128)        not null,
   PERTELEFONO          VARCHAR2(32),
   PERCELULAR           VARCHAR2(16)         not null,
   constraint PK_PERSONA primary key (PERIDENTIFICACION)
)
/

/*==============================================================*/
/* Index: HABITA_FK                                             */
/*==============================================================*/
create index HABITA_FK on PERSONA (
   CIUID ASC
)
/

/*==============================================================*/
/* Index: TIENETIPO_FK                                          */
/*==============================================================*/
create index TIENETIPO_FK on PERSONA (
   TIPPERID ASC
)
/

/*==============================================================*/
/* Index: TIENETIPOIDENTIFICACION_FK                            */
/*==============================================================*/
create index TIENETIPOIDENTIFICACION_FK on PERSONA (
   TIPIDEID ASC
)
/

/*==============================================================*/
/* Table: PQRSF                                                 */
/*==============================================================*/
create table PQRSF 
(
   PQSRFCODIGO          VARCHAR2(8)          not null,
   PERIDENTIFICACION    VARCHAR2(32)         not null,
   MEDID                NUMBER(1)            not null,
   RADID                VARCHAR2(32),
   TIPPQRSFID           NUMBER(1)            not null,
   PQRSFASUNTO          VARCHAR2(256)        not null,
   PQRSFDESCRIPCION     VARCHAR2(1024)       not null,
   PQRSFDIRECCIONADA    NUMBER(1)            default 0 not null
      constraint CKC_PQRSFDIRECCIONADA_PQRSF check (PQRSFDIRECCIONADA in (1,0)),
   PQRSFESTADO          NUMBER(1)            default 0 not null
      constraint CKC_PQRSFESTADO_PQRSF check (PQRSFESTADO in (0,1,2)),
   PQRSFFECHACREACION   DATE                 not null,
   PQRSFFECHAVENCIMIENTO DATE,
   PQRSFFECHACIERRE     DATE,
   constraint PK_PQRSF primary key (PQSRFCODIGO)
)
/

/*==============================================================*/
/* Index: EMANA_FK                                              */
/*==============================================================*/
create index EMANA_FK on PQRSF (
   PERIDENTIFICACION ASC
)
/

/*==============================================================*/
/* Index: TIENE_FK                                              */
/*==============================================================*/
create index TIENE_FK on PQRSF (
   RADID ASC
)
/

/*==============================================================*/
/* Index: TIENETIPOPQRSF_FK                                     */
/*==============================================================*/
create index TIENETIPOPQRSF_FK on PQRSF (
   TIPPQRSFID ASC
)
/

/*==============================================================*/
/* Index: TIENEMEDIOR_FK                                        */
/*==============================================================*/
create index TIENEMEDIOR_FK on PQRSF (
   MEDID ASC
)
/

/*==============================================================*/
/* Table: RADICADO                                              */
/*==============================================================*/
create table RADICADO 
(
   RADID                VARCHAR2(32)         not null,
   PQSRFCODIGO          VARCHAR2(8)          not null,
   USUUSUARIO           VARCHAR2(64)         not null,
   RADFECHA             DATE                 not null,
   constraint PK_RADICADO primary key (RADID)
)
/

/*==============================================================*/
/* Index: REGISTRA_FK                                           */
/*==============================================================*/
create index REGISTRA_FK on RADICADO (
   USUUSUARIO ASC
)
/

/*==============================================================*/
/* Index: TIENE2_FK                                             */
/*==============================================================*/
create index TIENE2_FK on RADICADO (
   PQSRFCODIGO ASC
)
/

/*==============================================================*/
/* Table: TIPOIDENTIFICACION                                    */
/*==============================================================*/
create table TIPOIDENTIFICACION 
(
   TIPIDEID             NUMBER(2)            not null,
   TIPIDEDESCRIPCION    VARCHAR2(64)         not null,
   constraint PK_TIPOIDENTIFICACION primary key (TIPIDEID)
)
/

/*==============================================================*/
/* Table: TIPOPERSONA                                           */
/*==============================================================*/
create table TIPOPERSONA 
(
   TIPPERID             NUMBER(2)            not null,
   TIPPERDESCRIPCION    VARCHAR2(64)         not null,
   constraint PK_TIPOPERSONA primary key (TIPPERID)
)
/

/*==============================================================*/
/* Table: TIPOPQRSF                                             */
/*==============================================================*/
create table TIPOPQRSF 
(
   TIPPQRSFID           NUMBER(1)            not null,
   TIPPQRSFDESCRIPCION  VARCHAR2(16)         not null,
   constraint PK_TIPOPQRSF primary key (TIPPQRSFID)
)
/

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
)
/

alter table CIUDAD
   add constraint FK_CIUDAD_PERTENECE_DEPARTAM foreign key (DEPTOID)
      references DEPARTAMENTO (DEPTOID)
/

alter table FUNCIONARIO
   add constraint FK_FUNCIONA_PERTENECE_DEPENDEN foreign key (DEPID)
      references DEPENDENCIA (DEPID)
/

alter table ORDEN
   add constraint FK_ORDEN_ASIGNA_USUARIO foreign key (USUUSUARIO)
      references USUARIO (USUUSUARIO)
/

alter table ORDEN
   add constraint FK_ORDEN_ATIENDE_FUNCIONA foreign key (FUNIDENTIFICACION)
      references FUNCIONARIO (FUNIDENTIFICACION)
/

alter table ORDEN
   add constraint FK_ORDEN_GENERA_PQRSF foreign key (PQSRFCODIGO)
      references PQRSF (PQSRFCODIGO)
/

alter table PERSONA
   add constraint FK_PERSONA_HABITA_CIUDAD foreign key (CIUID)
      references CIUDAD (CIUID)
/

alter table PERSONA
   add constraint FK_PERSONA_TIENETIPO_TIPOPERS foreign key (TIPPERID)
      references TIPOPERSONA (TIPPERID)
/

alter table PERSONA
   add constraint FK_PERSONA_TIENETIPO_TIPOIDEN foreign key (TIPIDEID)
      references TIPOIDENTIFICACION (TIPIDEID)
/

alter table PQRSF
   add constraint FK_PQRSF_EMANA_PERSONA foreign key (PERIDENTIFICACION)
      references PERSONA (PERIDENTIFICACION)
/

alter table PQRSF
   add constraint FK_PQRSF_TIENE_RADICADO foreign key (RADID)
      references RADICADO (RADID)
/

alter table PQRSF
   add constraint FK_PQRSF_TIENEMEDI_MEDIOREC foreign key (MEDID)
      references MEDIORECEPCION (MEDID)
/

alter table PQRSF
   add constraint FK_PQRSF_TIENETIPO_TIPOPQRS foreign key (TIPPQRSFID)
      references TIPOPQRSF (TIPPQRSFID)
/

alter table RADICADO
   add constraint FK_RADICADO_REGISTRA_USUARIO foreign key (USUUSUARIO)
      references USUARIO (USUUSUARIO)
/

alter table RADICADO
   add constraint FK_RADICADO_TIENE2_PQRSF foreign key (PQSRFCODIGO)
      references PQRSF (PQSRFCODIGO)
/


create or replace trigger COMPOUNDDELETETRIGGER_CIUDAD
for delete on CIUDAD compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_CIUDAD
for insert on CIUDAD compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_CIUDAD
for update on CIUDAD compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_CIUDAD before insert
on CIUDAD for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "CIUID" uses sequence CIUIDSEQUENCE
    select CIUIDSEQUENCE.NEXTVAL INTO :new.CIUID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_DEPARTAM
for delete on DEPARTAMENTO compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_DEPARTAM
for insert on DEPARTAMENTO compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_DEPARTAM
for update on DEPARTAMENTO compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_DEPARTAMENTO before insert
on DEPARTAMENTO for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "DEPTOID" uses sequence DEPTOIDSEQUENCE
    select DEPTOIDSEQUENCE.NEXTVAL INTO :new.DEPTOID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_DEPENDEN
for delete on DEPENDENCIA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_DEPENDEN
for insert on DEPENDENCIA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_DEPENDEN
for update on DEPENDENCIA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_DEPENDENCIA before insert
on DEPENDENCIA for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "DEPID" uses sequence DEPIDSEQUENCE
    select DEPIDSEQUENCE.NEXTVAL INTO :new.DEPID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_MEDIOREC
for delete on MEDIORECEPCION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_MEDIOREC
for insert on MEDIORECEPCION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_MEDIOREC
for update on MEDIORECEPCION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_MEDIORECEPCION before insert
on MEDIORECEPCION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "MEDID" uses sequence MEDIDSEQUENCE
    select MEDIDSEQUENCE.NEXTVAL INTO :new.MEDID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_ORDEN
for delete on ORDEN compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_ORDEN
for insert on ORDEN compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_ORDEN
for update on ORDEN compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_ORDEN before insert
on ORDEN for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ORDID" uses sequence ORDIDSEQUENCE
    select ORDIDSEQUENCE.NEXTVAL INTO :new.ORDID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_TIPOIDEN
for delete on TIPOIDENTIFICACION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_TIPOIDEN
for insert on TIPOIDENTIFICACION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_TIPOIDEN
for update on TIPOIDENTIFICACION compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_TIPOIDENTIFICACION before insert
on TIPOIDENTIFICACION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "TIPIDEID" uses sequence TIPIDEIDSEQUENCE
    select TIPIDEIDSEQUENCE.NEXTVAL INTO :new.TIPIDEID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_TIPOPERS
for delete on TIPOPERSONA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_TIPOPERS
for insert on TIPOPERSONA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_TIPOPERS
for update on TIPOPERSONA compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_TIPOPERSONA before insert
on TIPOPERSONA for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "TIPPERID" uses sequence TIPPERIDSEQUENCE
    select TIPPERIDSEQUENCE.NEXTVAL INTO :new.TIPPERID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create or replace trigger COMPOUNDDELETETRIGGER_TIPOPQRS
for delete on TIPOPQRSF compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDINSERTTRIGGER_TIPOPQRS
for insert on TIPOPQRSF compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create or replace trigger COMPOUNDUPDATETRIGGER_TIPOPQRS
for update on TIPOPQRSF compound trigger
// Declaration
// Body
  before statement is
  begin
     NULL;
  end before statement;

  before each row is
  begin
     NULL;
  end before each row;

  after each row is
  begin
     NULL;
  end after each row;

  after statement is
  begin
     NULL;
  end after statement;

END
/


create trigger TIB_TIPOPQRSF before insert
on TIPOPQRSF for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "TIPPQRSFID" uses sequence TIPPQRSFIDSEQUENCE
    select TIPPQRSFIDSEQUENCE.NEXTVAL INTO :new.TIPPQRSFID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


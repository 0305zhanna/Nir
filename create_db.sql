alter user c##nir quota 100m on SYSTEM;
grant unlimited tablespace to c##NIR;
CREATE SEQUENCE  "C##NIR"."SEQ_DISCIPLINE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 601 CACHE 20 ORDER  NOCYCLE  ;
CREATE SEQUENCE  "C##NIR"."SEQ_EMPLOYMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 ORDER  NOCYCLE  ;
CREATE SEQUENCE  "C##NIR"."SEQ_FLOWS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 401 CACHE 20 ORDER  NOCYCLE  ;
CREATE SEQUENCE  "C##NIR"."SEQ_GROUP_FLOW"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 461 CACHE 20 ORDER  NOCYCLE ;
CREATE SEQUENCE  "C##NIR"."SEQ_GROUPP"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE ;
CREATE SEQUENCE  "C##NIR"."SEQ_HANDBOOK"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE ;
CREATE SEQUENCE  "C##NIR"."SEQ_STUDY_LOAD"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1501 CACHE 20 ORDER  NOCYCLE ;
CREATE SEQUENCE  "C##NIR"."SEQ_TEACHERS"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 ORDER  NOCYCLE ;

  CREATE TABLE "C##NIR"."DISCIPLINE" 
   (	"ID" NUMBER(*,0), 
	"NAME" VARCHAR2(200 BYTE), 
	"BLOCK" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;

CREATE TABLE "C##NIR"."STUDY_LOAD" 
   (	"id" NUMBER(*,0), 
	"id_discipline" NUMBER(*,0), 
	"id_teacher" NUMBER(*,0), 
	"study_plan" VARCHAR2(200 BYTE), 
	"kind_occupat" VARCHAR2(200 BYTE), 
	"audience" NUMBER(*,0), 
	"other" VARCHAR2(200 BYTE),
    CONSTRAINT STUDY_LOAD_pk PRIMARY KEY ("id")
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
  
   CREATE TABLE "C##NIR"."TEACHERS" 
   (	"ID" NUMBER(*,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"DEGREE" VARCHAR2(100 BYTE),
    CONSTRAINT TEACHERS_pk PRIMARY KEY (id)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
  
  
    ALTER TABLE "C##NIR"."DISCIPLINE"
    ADD CONSTRAINT study_load_fk FOREIGN KEY ("ID")
    REFERENCES  "C##NIR"."STUDY_LOAD";
  
    ALTER TABLE "C##NIR"."STUDY_LOAD"
    ADD CONSTRAINT TEACH_fk FOREIGN KEY ("id_teacher")
    REFERENCES "C##NIR"."TEACHERS"; 
  
  
    CREATE TABLE "C##NIR"."GROUPP" 
    (	"ID" VARCHAR2(20 BYTE), 
        "number_student" NUMBER(*,0), 
        "number_budget" NUMBER(*,0), 
        "number_extrabudget" NUMBER(*,0),
        CONSTRAINT GROUPP_pk PRIMARY KEY (id)
    ) SEGMENT CREATION IMMEDIATE 
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
    NOCOMPRESS LOGGING
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
    BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "SYSTEM" ;
  
  DROP TABLE "C##NIR"."FLOWS" ;
  
  CREATE TABLE "C##NIR"."FLOWS"( 
   "ID" NUMBER(*,0), 
	"discipline" VARCHAR2(100 BYTE), 
	"id_teacher" NUMBER(*,0), 
	"number_semester" VARCHAR2(50 BYTE), 
	"group_load" VARCHAR2(100 BYTE),
    CONSTRAINT FLOWS_pk PRIMARY KEY (id)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
  
    DROP TABLE "C##NIR"."GROUP_FLOW" 

    CREATE TABLE "C##NIR"."GROUP_FLOW" 
    (	"id" NUMBER(*,0), 
        "group" VARCHAR2(20 BYTE), 
        "id_flow" NUMBER(*,0)
    ) SEGMENT CREATION IMMEDIATE 
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
    NOCOMPRESS LOGGING
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
    BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "SYSTEM" ;
  
    ALTER TABLE "C##NIR"."GROUP_FLOW"
    ADD CONSTRAINT GR_FL_GR_FK FOREIGN KEY ("group")
    REFERENCES "C##NIR"."GROUPP";
  
    ALTER TABLE "C##NIR"."GROUP_FLOW"
    ADD CONSTRAINT GROUP_FLOW_FK FOREIGN KEY ("id_flow")
    REFERENCES "C##NIR"."FLOWS";  
  
    CREATE TABLE "C##NIR"."EMPLOYMENT" 
    (   "ID" NUMBER(*,0), 
        "ID_TEACHER" NUMBER(*,0), 
        "TYPE" VARCHAR2(50 BYTE), 
        "POSITION" VARCHAR2(100 BYTE), 
        "RATE" FLOAT(126)
    ) SEGMENT CREATION IMMEDIATE 
    PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
    NOCOMPRESS LOGGING
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
    BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "SYSTEM" ;



  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_DISCIPLINE" (namee in varchar,blok in varchar) as 
    counter number; 
    begin 
    counter := 0; 
    select count(*)into counter from C##NIR.DISCIPLINE where name = namee and block = blok; 
    if (counter = 0) then 
    insert into C##NIR.DISCIPLINE(id,name,block) values (C##NIR.seq_discipline.nextval,namee,blok); 
    end if; 
  end pro_insert_discipline;

  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_EMPLOYMENT" (id_teacher in number,modee in varchar,positionn in varchar,rate in float) is
    begin
    insert into C##NIR.Employment(id,id_teacher,type,position,rate) values (C##NIR.seq_employment.nextval,id_teacher,modee,positionn,rate);
  end pro_insert_employment;

  DROP PROCEDURE "C##NIR"."PRO_INSERT_FLOWS"
  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_FLOWS" (potok in number, discipline in varchar2,id_teacher in number,semester in varchar2,grload in varchar) is
    counter number; 
    begin
    counter := 0; 
    select count(*)into counter from C##NIR.FLOWS where id like potok; 
    if (counter = 0) then 
    insert into C##NIR.FLOWS(id,"id_discipline","id_teacher","number_semester","group_load") values (potok,discipline,id_teacher,semester,grload);
    else update C##NIR.FLOWS set 
    "group_load" = grload where id = potok;
    end if;
  end pro_insert_flows;

  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_GROUP_FLOW" (groupp in varchar2,id_potok in number) is
    begin
    insert into C##NIR.GROUP_FLOW("id","group","id_flow") values (C##NIR.seq_group_flow.nextval,groupp,id_potok);
  end pro_insert_group_flow;

  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_GROUPP" (numGr in varchar2, student in number,budget in number,
    extrabudget in number) is
    counter number; 
    begin
    counter := 0; 
    select count(*)into counter from C##NIR.GROUPP where id like numGr; 
    if (counter = 0) then 
    insert into C##NIR.GROUPP("ID","number_student","number_budget","number_extrabudget") values (numGr,student,budget,extrabudget);
    else update C##NIR.GROUPP set 
    "number_student" = student,
    "number_budget" = budget,
    "number_extrabudget" = extrabudget where id = numGr;
    end if;
  end pro_insert_groupp;

  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_STUDY_LOAD" (id_discipline in number,id_teacher in number,study_plan in varchar,kind_occup in varchar,aud in number,ozer in varchar) is
    begin
    insert into C##NIR.STUDY_LOAD("id","id_discipline","id_teacher","study_plan","kind_occupat","audience","other") values (C##NIR.seq_study_load.nextval,id_discipline,id_teacher,study_plan,kind_occup,aud,ozer);
  end pro_insert_study_load;

  CREATE OR REPLACE PROCEDURE "C##NIR"."PRO_INSERT_TEACHERS" (namee in varchar,degre in varchar) is
    begin
    insert into C##NIR.Teachers("ID","NAME","DEGREE") values (C##NIR.seq_teachers.nextval,namee,degre);
  end pro_insert_teachers;

  CREATE OR REPLACE PROCEDURE "C##NIR"."RETURN_ID_FROM_DISCIPLINE" (
    in_name in C##NIR.discipline.name%type , 
    in_block in C##NIR.discipline.block%type , 
    out_id out C##NIR.discipline.id%type ) as
    BEGIN
        Select id into out_id from C##NIR.discipline where name = in_name and block = in_block; 
  END return_id_from_discipline;

  CREATE OR REPLACE PROCEDURE "C##NIR"."RETURN_ID_FROM_TEACHERS" (
    in_name in C##NIR.teachers.name%type , 
    out_id out C##NIR.teachers.id%type ) as
  BEGIN
    Select id into out_id from C##NIR.teachers where name like in_name; 
  END return_id_from_teachers;

CREATE TABLE PERSONA (
ID 		VARCHAR(20)	NOT NULL,
NOMBRE	VARCHAR(25) NOT NULL,
EMAIL 	VARCHAR(25) NOT NULL,
NAC 	DATE NOT NULL, 	
TEL		NUMBER(8),
PRIMARY KEY (ID)
);
/

CREATE SQUENCE ID_GRUPO START WITH 1;

CREATE TABLE GRUPO(
ID 		NUMBER(10) NOT NULL,
NUMERO	NUMBER(02) NOT NULL,
INICIO	DATE NOT NULL,
FINAL 	DATE NOT NULL,
PRIMARY KEY (ID)
);
/

CREATE TABLE PERSONAS_GRUPO(
ID_GRUPO	NUMBER(10) NOT NULL,
ID_PERSONA	VARCHAR(20) NOT NULL,
PRIMARY KEY (ID_GRUPO,ID_PERSONA),
FOREIGN KEY (ID_GRUPO) REFERENCES GRUPO (ID),
FOREIGN KEY (ID_PERSONA) REFERENCES PERSONA (ID)
);
/

CREATE OR REPLACE TRIGGER SEQUENCE_ID_GRUPO
BEFORE INSERT ON GRUPO FOR EACH ROW
BEGIN 
	SELECT ID_GRUPO.NEXTVAL
	INTO :NEW.ID
	FROM DUAL;
END;
/
	
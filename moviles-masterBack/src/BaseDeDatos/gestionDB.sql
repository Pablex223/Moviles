CREATE database `matricula`;

use `matricula`;

CREATE TABLE PERSONA (
CEDULA VARCHAR(12) NOT NULL,
PASS VARCHAR(255) NOT NULL,
TIPO INT NOT NULL,
NOMBRE VARCHAR(255) NOT NULL,
F_NACIMIENTO VARCHAR(255),
CORREO VARCHAR(255) NOT NULL,
TELEFONO INT NOT NULL,
CARRERA VARCHAR(10) NOT NULL,
CONSTRAINT PK_ESTUDIANTE PRIMARY KEY (CEDULA)
);

CREATE TABLE CARRERA (
CODIGO VARCHAR(12) NOT NULL,
NOMBRE VARCHAR(255) NOT NULL,
constraint pk_codigo_carrera primary key(CODIGO)
);
CREATE TABLE CICLO (
ID VARCHAR(6) NOT NULL,
ANNO INT NOT NULL,
NUM_CICLO INT NOT NULL,
FECHA_INICIO VARCHAR(10),
FECHA_FINAL VARCHAR(10),
CONSTRAINT PK_CICLO PRIMARY KEY (ID)

);

CREATE TABLE CURSO (
CODIGO VARCHAR(12) NOT NULL,
NOMBRE VARCHAR(255),
CREDITOS INT,
H_SEMANALES INT,
COD_CARRERA VARCHAR(12),
NUM_CICLO INT NOT NULL,
constraint pk_codigo_curso PRIMARY KEY (CODIGO),
constraint fk_codigo_carrera FOREIGN KEY (COD_CARRERA) REFERENCES CARRERA(CODIGO)
);

CREATE TABLE GRUPO (
ID_GRUPO VARCHAR(10)NOT NULL,
NUMERO INT NOT NULL,
ID_PROF VARCHAR(12)NOT NULL,
COD_CURSO VARCHAR(12)NOT NULL,
constraint PK_GRUPO PRIMARY KEY (ID_GRUPO),
constraint FK_PRO FOREIGN KEY (ID_PROF) REFERENCES PERSONA(CEDULA),
constraint FK_cod_cur FOREIGN KEY (COD_CURSO) REFERENCES CURSO(CODIGO)

);



CREATE TABLE NOTA
(NOTA INT DEFAULT 0, 
CURSO VARCHAR(12) NOT NULL, 
ESTUDIANTE VARCHAR(12) NOT NULL,
GRUPO varchar(10) NOT NULL,
CONDICION VARCHAR(12),
CONSTRAINT FK_ESTUDIANTE FOREIGN KEY (ESTUDIANTE) REFERENCES PERSONA (CEDULA),
CONSTRAINT FK_GRUPO FOREIGN KEY (GRUPO) REFERENCES GRUPO(ID_GRUPO),
CONSTRAINT FK_CURSO FOREIGN KEY (CURSO) REFERENCES CURSO(CODIGO)
);


INSERT INTO PERSONA (CEDULA,PASS,TIPO,NOMBRE,F_NACIMIENTO,CORREO,TELEFONO) VALUES('1','1',1,'ROOT',NULL,'@ ',855);

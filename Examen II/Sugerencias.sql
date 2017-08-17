CREATE DATABASE Sugerencias;

USE Sugerencias;

create table usuario (
 id varchar(20) not null primary key,
  clave varchar(20) not null,
  tipo integer 
);

create table sugerencia(
 id integer auto_increment not null primary key,
 usuario varchar(20) not null,
 texto varchar(50) not null,
 puntaje integer default 0
);

alter table sugerencia
  add constraint foreign key (usuario) references usuario (id);

insert into Usuario (id,clave,tipo) values ('estud99','estud99', 1);
insert into Usuario (id,clave,tipo) values ('visitante','visitante', 1);
insert into Usuario (id,clave,tipo) values ('admin','admin', 2);

insert into sugerencia (usuario,texto,puntaje) values ('estud99','Poner dispensador de golosinas',1);
insert into sugerencia (usuario,texto,puntaje) values ('estud99','Abrir laboratorios en la noche',3);
insert into sugerencia (usuario,texto,puntaje) values ('estud99','Ubicar proyectores mas alto en los lab',2);

insert into sugerencia (usuario,texto,puntaje) values ('visitante','Hacer pasillo techado a la soda',4);

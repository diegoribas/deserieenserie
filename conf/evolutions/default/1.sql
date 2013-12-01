# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table serie (
  id                        bigint not null,
  nombre                    varchar(255),
  descripcion               varchar(255),
  valoracion                double,
  genero                    varchar(255),
  constraint pk_serie primary key (id))
;

create table serie_usuario (
  id                        bigint not null,
  usuario_id                bigint,
  serie_id                  bigint,
  fuente                    varchar(255),
  temporada                 integer,
  capitulo                  integer,
  constraint pk_serie_usuario primary key (id))
;

create table usuario (
  id                        bigint not null,
  nick                      varchar(255),
  password                  varchar(255),
  constraint pk_usuario primary key (id))
;

create table valoracion_usuario (
  id                        bigint not null,
  valoracion                double,
  usuario_id                bigint,
  serie_id                  bigint,
  constraint pk_valoracion_usuario primary key (id))
;

create sequence serie_seq;

create sequence serie_usuario_seq;

create sequence usuario_seq;

create sequence valoracion_usuario_seq;

alter table serie_usuario add constraint fk_serie_usuario_usuario_1 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_serie_usuario_usuario_1 on serie_usuario (usuario_id);
alter table serie_usuario add constraint fk_serie_usuario_serie_2 foreign key (serie_id) references serie (id) on delete restrict on update restrict;
create index ix_serie_usuario_serie_2 on serie_usuario (serie_id);
alter table valoracion_usuario add constraint fk_valoracion_usuario_usuario_3 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_valoracion_usuario_usuario_3 on valoracion_usuario (usuario_id);
alter table valoracion_usuario add constraint fk_valoracion_usuario_serie_4 foreign key (serie_id) references serie (id) on delete restrict on update restrict;
create index ix_valoracion_usuario_serie_4 on valoracion_usuario (serie_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists serie;

drop table if exists serie_usuario;

drop table if exists usuario;

drop table if exists valoracion_usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists serie_seq;

drop sequence if exists serie_usuario_seq;

drop sequence if exists usuario_seq;

drop sequence if exists valoracion_usuario_seq;


/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     20/05/2013 16:33:00                          */
/*==============================================================*/


drop table if exists administrador;

drop table if exists contenedor;

drop table if exists estado_contenedor;

drop table if exists estado_punto_limpio;

drop table if exists inspector;

drop table if exists mantencion_punto_limpio;

drop table if exists materiales;

drop table if exists notificacion;

drop table if exists notificacion_de_sistema;

drop table if exists notificacion_de_usuario;

drop table if exists operario_mantencion;

drop table if exists punto_limpio;

drop table if exists revision_punto_limpio;

drop table if exists solicitud_mantencion;

drop table if exists tipo_incidencia;

drop table if exists usuario;

/*==============================================================*/
/* Table: administrador                                         */
/*==============================================================*/
create table administrador
(
   rut                  int not null,
   cod_administrador    int not null auto_increment comment 'Es un código para identificar a un administrador.',
   primary key (rut),
   key AK_cod_admininistrador (cod_administrador)
);

alter table administrador comment 'Representa a un usuario administrador del sistema que puede ';

/*==============================================================*/
/* Table: contenedor                                            */
/*==============================================================*/
create table contenedor
(
   num_contenedor       int not null auto_increment,
   id_material          int not null,
   num_punto_limpio     int not null comment 'Es un número utilizado para identificar a un punto limpio.',
   id_estado_contenedor int not null,
   capacidad_contenedor int comment 'Es la capacidad que tiene el contenedor para almacenar, la unidad utilizada se especifica en otro atributo.',
   unidad_medida_contenedor varchar(20) comment 'Es la unidad de medida en que se mide la capacidad del punto limpio.',
   porcentaje_uso_contenedor int comment 'Es un porcentaje que representa el uso que tiene el punto limpio, 0% significa que acaba de ser vaciado y 100% representa que se encuentra lleno.',
   porcentaje_uso_estimado_contenedor int comment 'Es un porcentaje que representa el uso (llenado) del contenedor según las estimaciones del sistema a la fecha actual.',
   primary key (num_contenedor)
);

alter table contenedor comment 'Representa a un contenedor de residuos que se encuentra en u';

/*==============================================================*/
/* Table: estado_contenedor                                     */
/*==============================================================*/
create table estado_contenedor
(
   id_estado_contenedor int not null auto_increment,
   nombre_estado_contenedor varchar(50),
   primary key (id_estado_contenedor)
);

/*==============================================================*/
/* Table: estado_punto_limpio                                   */
/*==============================================================*/
create table estado_punto_limpio
(
   id_estado_punto_limpio int not null auto_increment,
   nombre_estado_punto_limpio varchar(50) not null,
   primary key (id_estado_punto_limpio)
);

/*==============================================================*/
/* Table: inspector                                             */
/*==============================================================*/
create table inspector
(
   rut                  int not null,
   cod_inspector        int not null auto_increment comment 'Es un código para identificar a un inspector.',
   primary key (rut),
   key AK_cod_inspector (cod_inspector)
);

alter table inspector comment 'Representa a un usuario inspector registrado en el sistema.';

/*==============================================================*/
/* Table: mantencion_punto_limpio                               */
/*==============================================================*/
create table mantencion_punto_limpio
(
   num_mant             int not null auto_increment comment 'Es un número utilizado para identificar a una mantención del punto limpio.',
   num_punto_limpio     int not null comment 'Es un número utilizado para identificar a un punto limpio.',
   rut                  int not null,
   fecha_mant           datetime comment 'Es la fecha en que fue realizada dicha mantención del punto limpio.',
   comentarios_mant     varchar(254) comment 'Es un comentario o anotaciones que se hayan hecho luego de la mantención del punto limpio.',
   primary key (num_mant)
);

alter table mantencion_punto_limpio comment 'Representa una mantención o retiro de desechos que se haya r';

/*==============================================================*/
/* Table: materiales                                            */
/*==============================================================*/
create table materiales
(
   id_material          int not null auto_increment,
   nombre_material      varchar(100) comment 'Es el nombre o descripción corta de un tipo de material de desecho que se almacena en algún contenedor de los puntos limpios.',
   primary key (id_material)
);

alter table materiales comment 'Representa a cierto tipo de material que puede almacenar un ';

/*==============================================================*/
/* Table: notificacion                                          */
/*==============================================================*/
create table notificacion
(
   num_notificacion     int not null auto_increment comment 'Es un número utilizado para identificar un aviso generado por un usuario del punto limpio.',
   id_tipo_incidencia   int not null,
   num_punto_limpio     int comment 'Es un número utilizado para identificar a un punto limpio.',
   comentario_notificacion varchar(254) comment 'Es un comentario o descripción del aviso que se está dando, para así detallar la incidencia existente en el punto limpio.',
   revisado_notificacion bool comment 'Es un indicador para saber si un aviso generado por un usuario del punto limpio ha sido revisado por el inspector que le corresponda.',
   fecha_hora_notificacion datetime comment 'Es la fecha y hora en que es generado este aviso por parte del usuario del punto limpio.',
   resuelto_notificacion bool comment 'Es un indicador para saber si se ha resuelto la incidencia que el usuario del punto limpio ha informado.',
   primary key (num_notificacion)
);

alter table notificacion comment 'Representa un aviso que es generado por un usuario del punto';

/*==============================================================*/
/* Table: notificacion_de_sistema                               */
/*==============================================================*/
create table notificacion_de_sistema
(
   num_notificacion     int not null comment 'Es un número utilizado para identificar un aviso generado por un usuario del punto limpio.',
   num_notificacion_sistema int not null auto_increment,
   primary key (num_notificacion),
   key AK_num_notif_sistema (num_notificacion_sistema)
);

/*==============================================================*/
/* Table: notificacion_de_usuario                               */
/*==============================================================*/
create table notificacion_de_usuario
(
   num_notificacion     int not null comment 'Es un número utilizado para identificar un aviso generado por un usuario del punto limpio.',
   num_notificacion_usuario int not null auto_increment,
   email_contacto       varchar(254),
   imagen_adjunta       varchar(254),
   primary key (num_notificacion),
   key AK_num_notif_usuario (num_notificacion_usuario)
);

alter table notificacion_de_usuario comment 'Representa a una notificación cuando es realizada por un usu';

/*==============================================================*/
/* Table: operario_mantencion                                   */
/*==============================================================*/
create table operario_mantencion
(
   rut                  int not null,
   cod_operario         int not null auto_increment comment 'Es un código para identificar a un operario de mantención.',
   primary key (rut),
   key AK_cod_operario (cod_operario)
);

alter table operario_mantencion comment 'Representa a un usuario operario de mantención registrado en';

/*==============================================================*/
/* Table: punto_limpio                                          */
/*==============================================================*/
create table punto_limpio
(
   num_punto_limpio     int not null auto_increment comment 'Es un número utilizado para identificar a un punto limpio.',
   id_estado_punto_limpio int not null,
   rut                  int,
   nombre_punto_limpio  varchar(254) not null comment 'Es un nombre que puede tener un punto limpio para poder ser recordado más fácilmente.',
   comuna_punto_limpio  varchar(254) comment 'Es la comuna donde se encuentra el punto limpio.',
   ubicacion_punto_limpio varchar(254) comment 'Es la dirección del punto limpio, expresado como calle, N°, intersección de calles o referencias (Ejemplo: Dentro del Mall Vespucio).',
   latitud_punto_limpio int comment 'Es la latitud del punto limpio según coordenadas geográficas.',
   longitud_punto_limpio int comment 'Es la longitud del punto limpio según coordenadas geográficas.',
   estado_global_punto_limpio varchar(254) not null comment 'Es un nombre o descripción corta del estado global en que se encuentra el punto limpio (Se recomienda no tenga más de 15 letras de largo).',
   fecha_proxima_revision_punto_limpio datetime not null comment 'Indica la fecha estimada para la próxima revisión del punto limpio.',
   primary key (num_punto_limpio)
);

alter table punto_limpio comment 'Representa un punto limpio en el sistema.';

/*==============================================================*/
/* Table: revision_punto_limpio                                 */
/*==============================================================*/
create table revision_punto_limpio
(
   num_rev              int not null auto_increment comment 'Es un número utilizado para indentificar a una revisión de un punto limpio.',
   num_punto_limpio     int not null comment 'Es un número utilizado para identificar a un punto limpio.',
   rut                  int not null,
   fecha_rev            datetime comment 'Es la fecha en que fue realizada dicha revisión del punto limpio por parte del inspector.',
   comentarios_rev      varchar(254) comment 'Es un comentario o anotaciones que se hayan hecho luego de la revisión del punto limpio realizada por el inspector.',
   primary key (num_rev)
);

alter table revision_punto_limpio comment 'Representa una visita de revisión que haya realizado un insp';

/*==============================================================*/
/* Table: solicitud_mantencion                                  */
/*==============================================================*/
create table solicitud_mantencion
(
   num_sol_mant         int not null auto_increment comment 'Es un número utilizado para identificar a una solicitud de mantención.',
   num_rev              int comment 'Es un número utilizado para indentificar a una revisión de un punto limpio.',
   num_punto_limpio     int not null comment 'Es un número utilizado para identificar a un punto limpio.',
   rut                  int not null,
   ins_rut              int not null,
   fecha_sol_mant       datetime comment 'Es la fecha en que fue realizada la solicitud.',
   comentarios_sol_mant varchar(254) comment 'Es un comentario o anotaciones que se incluyen en la solicitud de mantención.',
   primary key (num_sol_mant)
);

alter table solicitud_mantencion comment 'Representa una solicitud que realiza el inspector para que s';

/*==============================================================*/
/* Table: tipo_incidencia                                       */
/*==============================================================*/
create table tipo_incidencia
(
   id_tipo_incidencia   int not null auto_increment,
   nombre_incidencia    varchar(50) comment 'Es el nombre o descripción corta de un tipo de incidencia que puede hacer un usuario de los puntos limpios.',
   visible_usuario      bool comment 'Es un indicador para saber si este es un tipo de incidencia visible a los usuarios del punto limpio, así diferencias las incidencias que genera automáticamente el sistema.',
   primary key (id_tipo_incidencia)
);

alter table tipo_incidencia comment 'Representa los posibles tipos de incidencia que puede tener ';

/*==============================================================*/
/* Table: usuario                                               */
/*==============================================================*/
create table usuario
(
   username             varchar(50) not null comment 'Es el nombre de usuario utilizado para ingresar al sistema.',
   rut                  int not null,
   password             varchar(32) not null comment 'Es la contraseña utilizada para ingresar al sistema.',
   nombre               varchar(50) not null comment 'Es el nombre real del usuario, sólo su nombre.',
   apellido1            varchar(50) not null comment 'Es el primer apellido del usuario.',
   apellido2            int comment 'Es el segundo apellido del usuario.',
   email                varchar(254) not null comment 'Es la dirección de correo electrónico del usuario.',
   primary key (rut),
   key AK_IDENTIFIER_1 (username)
);

alter table usuario comment 'Representa a un usuario registrado en el sistema';

alter table administrador add constraint FK_USUARIO1 foreign key (rut)
      references usuario (rut) on delete restrict on update restrict;

alter table contenedor add constraint FK_Association_1 foreign key (num_punto_limpio)
      references punto_limpio (num_punto_limpio) on delete restrict on update restrict;

alter table contenedor add constraint FK_Association_6 foreign key (id_material)
      references materiales (id_material) on delete restrict on update restrict;

alter table contenedor add constraint FK_Relationship_14 foreign key (id_estado_contenedor)
      references estado_contenedor (id_estado_contenedor) on delete restrict on update restrict;

alter table inspector add constraint FK_USUARIO3 foreign key (rut)
      references usuario (rut) on delete restrict on update restrict;

alter table mantencion_punto_limpio add constraint FK_Association_10 foreign key (rut)
      references operario_mantencion (rut) on delete restrict on update restrict;

alter table mantencion_punto_limpio add constraint FK_Association_7 foreign key (num_punto_limpio)
      references punto_limpio (num_punto_limpio) on delete restrict on update restrict;

alter table notificacion add constraint FK_Association_3 foreign key (id_tipo_incidencia)
      references tipo_incidencia (id_tipo_incidencia) on delete restrict on update restrict;

alter table notificacion add constraint FK_Association_4 foreign key (num_punto_limpio)
      references punto_limpio (num_punto_limpio) on delete restrict on update restrict;

alter table notificacion_de_sistema add constraint FK_INHERITANCE_NOTIF2 foreign key (num_notificacion)
      references notificacion (num_notificacion) on delete cascade on update cascade;

alter table notificacion_de_usuario add constraint FK_INHERITANCE_NOTIF1 foreign key (num_notificacion)
      references notificacion (num_notificacion) on delete cascade on update cascade;

alter table operario_mantencion add constraint FK_USUARIO2 foreign key (rut)
      references usuario (rut) on delete restrict on update restrict;

alter table punto_limpio add constraint FK_Association_2 foreign key (rut)
      references inspector (rut) on delete set null on update cascade;

alter table punto_limpio add constraint FK_Relationship_15 foreign key (id_estado_punto_limpio)
      references estado_punto_limpio (id_estado_punto_limpio) on delete restrict on update restrict;

alter table revision_punto_limpio add constraint FK_Association_11 foreign key (rut)
      references inspector (rut) on delete restrict on update restrict;

alter table revision_punto_limpio add constraint FK_Association_9 foreign key (num_punto_limpio)
      references punto_limpio (num_punto_limpio) on delete restrict on update restrict;

alter table solicitud_mantencion add constraint FK_Association_14 foreign key (rut)
      references operario_mantencion (rut) on delete restrict on update restrict;

alter table solicitud_mantencion add constraint FK_Association_15 foreign key (ins_rut)
      references inspector (rut) on delete restrict on update restrict;

alter table solicitud_mantencion add constraint FK_Association_16 foreign key (num_punto_limpio)
      references punto_limpio (num_punto_limpio) on delete restrict on update restrict;

alter table solicitud_mantencion add constraint FK_Association_17 foreign key (num_rev)
      references revision_punto_limpio (num_rev) on delete restrict on update restrict;


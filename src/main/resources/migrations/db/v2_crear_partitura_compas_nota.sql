create table partitura
(
	partitura_id bigint not null
		constraint partitura_pkey
			primary key
		constraint fk_usuario
			references usuario,
	denominador varchar(255),
	nombre varchar(255),
	numerador varchar(255),
	tonalidad varchar(255),
	id bigint not null
		constraint fk_partitura_usuario
			references usuario
);

create table compas
(
	compas_id bigint not null
		constraint compas_pkey
			primary key
		constraint fk_partitura
			references partitura,
	partitura_id bigint not null
		constraint fk_compas_partitura
			references partitura
);

create table nota
(
	nota_id bigint not null
		constraint nota_pkey
			primary key
		constraint fk_compas
			references compas,
	clarity integer,
	duracion varchar(255),
	error integer,
	has_dot boolean,
	has_tie boolean,
	pitch varchar(255),
	compas_id bigint not null
		constraint fk_nota_compas
			references compas
);

alter table nota owner to kazoo;
alter table compas owner to kazoo;
alter table partitura owner to kazoo;


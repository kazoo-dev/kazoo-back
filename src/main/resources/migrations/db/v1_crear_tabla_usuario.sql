create table if not exists usuario
(
	partitura_id bigint not null
		constraint usuario_pkey
			primary key,
	clave varchar(255) not null,
	nombre varchar(255) not null
		constraint nombre_key unique,
	salt bytea
);
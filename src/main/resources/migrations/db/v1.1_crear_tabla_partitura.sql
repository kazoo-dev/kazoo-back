CREATE TABLE IF NOT EXISTS partitura (
    id bigint NOT NULL PRIMARY KEY,
    notas varchar(10000),
    nombre varchar(255),
    usuario_id bigint NOT NULL
);
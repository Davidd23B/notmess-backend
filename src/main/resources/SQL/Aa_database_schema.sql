-- ==========================================================
--  BASE DE DATOS: PostgreSQL
-- ==========================================================

-- ==========================================================
--  TABLA: usuario
-- ==========================================================
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    passwd VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('admin', 'usuario')),
    activo BOOLEAN DEFAULT TRUE
);


-- ==========================================================
--  TABLA: categoria_producto
-- ==========================================================
CREATE TABLE categoria_producto (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);


INSERT INTO categoria_producto (nombre) VALUES
    ('Carnes'), ('Pescados'), ('Verduras'), ('Frutas'),
    ('Lácteos'), ('Salsas'), ('Aceites'), ('Cereales'),
    ('Conservas'), ('Congelados'), ('Bebidas'), ('Panadería');

-- ==========================================================
--  TABLA: producto
-- ==========================================================
CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad NUMERIC(10,2) DEFAULT 0,
    medida VARCHAR(10) NOT NULL CHECK (medida IN ('unidad', 'kg', 'L')),
    proveedor VARCHAR(100),
    imagen VARCHAR(255),
    id_categoria INTEGER REFERENCES categoria_producto(id_categoria),
    CONSTRAINT check_cantidad_entera CHECK (
        (medida = 'unidad' AND cantidad = FLOOR(cantidad))
        OR (medida IN ('kg', 'L'))
    )
);


-- ==========================================================
--  TABLA: albaran
-- ==========================================================
CREATE TABLE albaran (
    id_albaran SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('entrada', 'salida', 'merma')),
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    motivo_merma TEXT DEFAULT NULL,
    id_usuario INTEGER NOT NULL REFERENCES usuario(id_usuario)
);


-- ==========================================================
--  TABLA: linea_albaran
-- ==========================================================
CREATE TABLE linea_albaran (
	id_linea_albaran SERIAL PRIMARY KEY,
	cantidad NUMERIC(10,2) DEFAULT 0,
	id_albaran INTEGER NOT NULL REFERENCES albaran(id_albaran),
	id_producto INTEGER NOT NULL REFERENCES producto(id_producto)
);


-- ==========================================================
--  TABLA PRINCIPAL: appcc
-- ==========================================================
CREATE TABLE appcc (
    id_appcc SERIAL PRIMARY KEY,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha DATE GENERATED ALWAYS AS (DATE(fecha_hora)) STORED,
    turno VARCHAR(10) NOT NULL CHECK (turno IN ('mañana', 'tarde')),
    completado BOOLEAN DEFAULT FALSE,
    observaciones TEXT,
    id_usuario INTEGER REFERENCES usuario(id_usuario),
    CONSTRAINT unique_appcc_dia_turno UNIQUE (fecha, turno)
);


-- ==========================================================
--  SUBTABLA: appcc_limpieza
-- ==========================================================
CREATE TABLE appcc_limpieza (
    id_appcc_limpieza SERIAL PRIMARY KEY,
    id_appcc INTEGER NOT NULL REFERENCES appcc(id_appcc) ON DELETE CASCADE,
    congelador1 BOOLEAN,
    congelador2 BOOLEAN,
    congelador3 BOOLEAN,
    camara1 BOOLEAN,
    camara2 BOOLEAN,
    mesa1 BOOLEAN,
    mesa2 BOOLEAN,
    mesa3 BOOLEAN,
    paredes BOOLEAN,
    suelo BOOLEAN,
    observaciones TEXT,
    CONSTRAINT unique_appcc_limpieza UNIQUE (id_appcc)
);


-- ==========================================================
--  SUBTABLA: appcc_temperatura
-- ==========================================================
CREATE TABLE appcc_temperatura (
    id_appcc_temperatura SERIAL PRIMARY KEY,
    id_appcc INTEGER NOT NULL REFERENCES appcc(id_appcc) ON DELETE CASCADE,
    congelador1 NUMERIC(4,1),
    congelador2 NUMERIC(4,1),
    congelador3 NUMERIC(4,1),
    camara1 NUMERIC(4,1),
    camara2 NUMERIC(4,1),
    mesa1 NUMERIC(4,1),
    mesa2 NUMERIC(4,1),
    mesa3 NUMERIC(4,1),
    observaciones TEXT,
    CONSTRAINT unique_appcc_temperatura UNIQUE (id_appcc)
);


-- ==========================================================
--  SUBTABLA: appcc_producto
-- ==========================================================
CREATE TABLE appcc_producto (
    id_appcc_producto SERIAL PRIMARY KEY,
    id_appcc INTEGER NOT NULL REFERENCES appcc(id_appcc) ON DELETE CASCADE,
    estado_producto_congelador1 VARCHAR(50),
    estado_producto_congelador2 VARCHAR(50),
    estado_producto_congelador3 VARCHAR(50),
    estado_producto_camara1 VARCHAR(50),
    estado_producto_camara2 VARCHAR(50),
    estado_producto_mesa1 VARCHAR(50),
    estado_producto_mesa2 VARCHAR(50),
    estado_producto_mesa3 VARCHAR(50),
    temperatura_producto_congelador1 NUMERIC(4,1),
    temperatura_producto_congelador2 NUMERIC(4,1),
    temperatura_producto_congelador3 NUMERIC(4,1),
    temperatura_producto_camara1 NUMERIC(4,1),
    temperatura_producto_camara2 NUMERIC(4,1),
    temperatura_producto_mesa1 NUMERIC(4,1),
    temperatura_producto_mesa2 NUMERIC(4,1),
    temperatura_producto_mesa3 NUMERIC(4,1),
    observaciones TEXT,
    CONSTRAINT unique_appcc_producto UNIQUE (id_appcc)
);


-- ==========================================================
--  SUBTABLA: appcc_freidora
-- ==========================================================
CREATE TABLE appcc_freidora (
    id_appcc_freidora SERIAL PRIMARY KEY,
    id_appcc INTEGER NOT NULL REFERENCES appcc(id_appcc) ON DELETE CASCADE,
    temperatura_freidora1 NUMERIC(4,1),
    temperatura_freidora2 NUMERIC(4,1),
    tpm_freidora1 NUMERIC(4,1),
    tpm_freidora2 NUMERIC(4,1),
    observaciones TEXT,
    CONSTRAINT unique_appcc_freidora UNIQUE (id_appcc)
);


-- ==========================================================
--  ÍNDICES para optimizar rendimiento
-- ==========================================================
CREATE INDEX idx_producto_categoria ON producto(id_categoria);
CREATE INDEX idx_albaran_producto ON albaran(id_producto);
CREATE INDEX idx_albaran_usuario ON albaran(id_usuario);
CREATE INDEX idx_albaran_fecha_hora ON albaran(fecha_hora);
CREATE INDEX idx_appcc_fecha_turno ON appcc(fecha, turno);
CREATE INDEX idx_appcc_limpieza ON appcc_limpieza(id_appcc);
CREATE INDEX idx_appcc_temperatura ON appcc_temperatura(id_appcc);
CREATE INDEX idx_appcc_producto ON appcc_producto(id_appcc);
CREATE INDEX idx_appcc_freidora ON appcc_freidora(id_appcc);

CREATE TABLE albaran (
    id_albaran SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('entrada', 'salida', 'merma')),
    cantidad NUMERIC(10,2) NOT NULL,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT,
    motivo_merma TEXT DEFAULT NULL,
    id_producto INTEGER NOT NULL REFERENCES producto(id_producto),
    id_usuario INTEGER NOT NULL REFERENCES usuario(id_usuario)
);
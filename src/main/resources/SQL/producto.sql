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
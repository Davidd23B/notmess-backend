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
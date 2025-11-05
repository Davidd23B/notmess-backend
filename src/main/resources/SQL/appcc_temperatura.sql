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
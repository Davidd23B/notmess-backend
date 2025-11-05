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
)
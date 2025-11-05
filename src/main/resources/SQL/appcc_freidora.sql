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
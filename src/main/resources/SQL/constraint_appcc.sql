ALTER TABLE appcc
    ADD CONSTRAINT fk_appcc_limpieza
    FOREIGN KEY (id_appcc_limpieza) REFERENCES appcc_limpieza(id_appcc_limpieza) ON DELETE SET NULL;

ALTER TABLE appcc
    ADD CONSTRAINT fk_appcc_temperatura
    FOREIGN KEY (id_appcc_temperatura) REFERENCES appcc_temperatura(id_appcc_temperatura) ON DELETE SET NULL;

ALTER TABLE appcc
    ADD CONSTRAINT fk_appcc_producto
    FOREIGN KEY (id_appcc_producto) REFERENCES appcc_producto(id_appcc_producto) ON DELETE SET NULL;

ALTER TABLE appcc
    ADD CONSTRAINT fk_appcc_freidora
    FOREIGN KEY (id_appcc_freidora) REFERENCES appcc_freidora(id_appcc_freidora) ON DELETE SET NULL;

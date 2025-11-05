CREATE INDEX idx_producto_categoria ON producto(id_categoria);
CREATE INDEX idx_albaran_producto ON albaran(id_producto);
CREATE INDEX idx_albaran_usuario ON albaran(id_usuario);
CREATE INDEX idx_albaran_fecha_hora ON albaran(fecha_hora);
CREATE INDEX idx_appcc_fecha_turno ON appcc(fecha, turno);
CREATE INDEX idx_appcc_limpieza ON appcc_limpieza(id_appcc);
CREATE INDEX idx_appcc_temperatura ON appcc_temperatura(id_appcc);
CREATE INDEX idx_appcc_producto ON appcc_producto(id_appcc);
CREATE INDEX idx_appcc_freidora ON appcc_freidora(id_appcc);
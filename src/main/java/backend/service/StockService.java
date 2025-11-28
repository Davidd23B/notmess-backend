package backend.service;

import backend.model.LineaAlbaran;

public interface StockService {
    void actualizarStock(LineaAlbaran lineaAlbaran);
    void revertirStock(LineaAlbaran lineaAlbaran);
    boolean verificarStock(LineaAlbaran lineaAlbaran);
}
package backend.service;

import backend.model.LineaAlbaran;
import backend.model.Albaran;
import backend.model.Producto;
import java.util.List;

public interface LineaAlbaranService {
    List<LineaAlbaran> findAll();
    LineaAlbaran findById(Long id);
    LineaAlbaran create(LineaAlbaran lineaAlbaran, Albaran albaran, Producto producto);
    LineaAlbaran update(Long id, LineaAlbaran lineaAlbaran);
    void deleteById(Long id);
    List<LineaAlbaran> findByAlbaran(Albaran albaran);
    List<LineaAlbaran> findByProducto(Producto producto);
}
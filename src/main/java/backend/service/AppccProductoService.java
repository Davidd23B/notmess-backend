package backend.service;

import backend.model.AppccProducto;
import java.util.List;

public interface AppccProductoService {
    List<AppccProducto> findAll();
    AppccProducto findById(Long id);
    AppccProducto create(AppccProducto a);
    AppccProducto update(Long id, AppccProducto appccProducto);
    void deleteById(Long id);
}


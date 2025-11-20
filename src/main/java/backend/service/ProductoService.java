package backend.service;

import backend.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto create(Producto p);
    Producto update(Long id, Producto producto);
    void deleteById(Long id);
}

package backend.service;

import backend.model.AppccProducto;
import java.util.List;
import java.util.Optional;

public interface AppccProductoService {
    List<AppccProducto> findAll();
    AppccProducto findById(Long id);
    Optional<AppccProducto> findByAppccId(Long idAppcc);
    AppccProducto create(AppccProducto a);
    AppccProducto update(Long id, AppccProducto appccProducto);
    void deleteById(Long id);
}


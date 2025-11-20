package backend.service;

import backend.model.CategoriaProducto;
import java.util.List;

public interface CategoriaProductoService {
    List<CategoriaProducto> findAll();
    CategoriaProducto findById(Long id);
    CategoriaProducto create(CategoriaProducto categoria);
    CategoriaProducto update(Long id, CategoriaProducto categoria);
    void deleteById(Long id);
}


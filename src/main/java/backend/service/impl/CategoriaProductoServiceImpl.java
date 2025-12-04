package backend.service.impl;

import backend.model.CategoriaProducto;
import backend.model.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.CategoriaProductoRepository;
import backend.repository.ProductoRepository;
import backend.service.CategoriaProductoService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoRepository categoriaRepo;
    private final ProductoRepository productoRepo;

    @Override
    public List<CategoriaProducto> findAll(){
        return categoriaRepo.findAll();
    }

    @Override
    public CategoriaProducto findById(Long id){
        return categoriaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada: " + id));
    }

    @Override
    public CategoriaProducto create(CategoriaProducto categoria){
        if(categoriaRepo.existsByNombreIgnoreCase(categoria.getNombre().trim())){
            throw new IllegalArgumentException("La categoria de producto ya existe");
        }
        categoria.setNombre(categoria.getNombre().trim());
        return categoriaRepo.save(categoria);
    }

    @Override
    public CategoriaProducto update(Long id, CategoriaProducto categoria){
        CategoriaProducto c = findById(id);
        if(categoria.getNombre() != null && !categoria.getNombre().trim().equalsIgnoreCase(c.getNombre())){
            if(categoriaRepo.existsByNombreIgnoreCase(categoria.getNombre().trim())){
                throw new IllegalArgumentException("La categoria de producto ya existe");
            }
            c.setNombre(categoria.getNombre().trim());
        }
        return categoriaRepo.save(c);
    }

    @Override
    public void deleteById(Long id){
        CategoriaProducto c = categoriaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada: " + id));
        
        List<Producto> productosAsociados = productoRepo.findByCategoria(c);
        if (!productosAsociados.isEmpty()) {
            throw new IllegalArgumentException(
                "No se puede eliminar la categoría porque tiene " + 
                productosAsociados.size() + " producto(s) asociado(s). " +
                "Primero debe reasignar o eliminar los productos de esta categoría."
            );
        }
        
        categoriaRepo.delete(c);
    }
}

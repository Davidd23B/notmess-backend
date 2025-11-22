package backend.service.impl;

import backend.model.Producto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.ProductoRepository;
import backend.service.ImagenService;
import backend.service.ProductoService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepo;
    private final ImagenService imagenService;


    @Override
    public List<Producto> findAll(){
        return productoRepo.findAll();
    }

    @Override
    public Producto findById(Long id){
        return productoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
    }

    @Override
    public Producto create(Producto producto){
        if(productoRepo.existsByNombreIgnoreCase(producto.getNombre().trim())){
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        producto.setNombre(producto.getNombre().trim());
        producto.setProveedor(producto.getProveedor().trim());
        return productoRepo.save(producto);
    }

    @Override
    public Producto update(Long id, Producto producto){
        Producto p = findById(id);
        if(producto.getNombre() != null && !producto.getNombre().trim().equalsIgnoreCase(p.getNombre())){
            if(productoRepo.existsByNombreIgnoreCase(producto.getNombre().trim())){
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
            p.setNombre(producto.getNombre().trim());
        }
        if(producto.getCantidad() != null) p.setCantidad(producto.getCantidad());
        if(producto.getMedida() != null) p.setMedida(producto.getMedida());
        if(producto.getProveedor() != null) p.setProveedor(producto.getProveedor());
        if(producto.getImagen() != null) {
            if(p.getImagen() != null && !p.getImagen().equals(producto.getImagen())){
                imagenService.deleteImagen(p.getImagen());
            }
            p.setImagen(producto.getImagen());
        }
        if(producto.getCategoria() != null) p.setCategoria(producto.getCategoria());
        return productoRepo.save(p);
    }

    @Override
    public void deleteById(Long id){
        Producto p = productoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + id));
        if(p.getImagen() != null){
            imagenService.deleteImagen(p.getImagen());
        }
        productoRepo.delete(p);
    }
}

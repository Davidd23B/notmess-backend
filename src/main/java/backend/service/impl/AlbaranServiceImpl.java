package backend.service.impl;

import backend.model.Albaran;
import backend.model.Producto;
import backend.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AlbaranRepository;
import backend.repository.UsuarioRepository;
import backend.repository.ProductoRepository;
import backend.service.AlbaranService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbaranServiceImpl implements AlbaranService {

    private final AlbaranRepository albaranRepo;
    private final UsuarioRepository usuarioRepo;
    private final ProductoRepository productoRepo;

    @Override
    public List<Albaran> findAll(){
        return albaranRepo.findAll();
    }

    @Override
    public Albaran findById(Long id){
        return albaranRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Albaran no encontrado: " + id));
    }

    @Override
    public Albaran create(Albaran albaran, Usuario usuario, Producto producto){
        Usuario u = usuarioRepo.findById(usuario.getId_usuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + usuario.getId_usuario()));
        Producto p = productoRepo.findById(producto.getId_producto()).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + producto.getId_producto()));
        
        if(albaran.getCantidad() == null || albaran.getCantidad() <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        if(albaran.getTipo() != null && albaran.getTipo().trim().equalsIgnoreCase("entrada")){
            p.setCantidad(p.getCantidad() == null ? 0.0 : p.getCantidad() + albaran.getCantidad());
        } else if(albaran.getTipo() != null && albaran.getTipo().trim().equalsIgnoreCase("salida") || albaran.getTipo().trim().equalsIgnoreCase("merma")){
            if(producto.getCantidad() != null && producto.getCantidad() >= albaran.getCantidad()){
                p.setCantidad(p.getCantidad() - albaran.getCantidad());
            } else {
                throw new IllegalArgumentException("No hay suficiente stock para realizar la salida o merma");
            }
        }
        albaran.setFechaHora(LocalDateTime.now());
        productoRepo.save(producto);
        albaran.setUsuario(u);
        albaran.setProducto(p);
        return albaranRepo.save(albaran);
    }

    @Override
    public void deleteById(Long id){
        Albaran a = albaranRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Albaran no encontrado: " + id));
        albaranRepo.delete(a);
    }

    @Override
    public List<Albaran> findByFecha(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().atTime(23, 59, 59);
        return albaranRepo.findByFechaHoraBetween(inicio, fin);
    }
}

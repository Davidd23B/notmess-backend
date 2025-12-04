package backend.service.impl;

import backend.model.Albaran;
import backend.model.LineaAlbaran;
import backend.model.Producto;
import backend.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AlbaranRepository;
import backend.repository.LineaAlbaranRepository;
import backend.repository.UsuarioRepository;
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
    private final LineaAlbaranRepository lineaAlbaranRepo;

    @Override
    public List<Albaran> findAll(){
        return albaranRepo.findAll();
    }

    @Override
    public Albaran findById(Long id){
        return albaranRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Albaran no encontrado: " + id));
    }

    @Override
    public Albaran create(Albaran albaran, Usuario usuario){
        Usuario u = usuarioRepo.findById(usuario.getId_usuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + usuario.getId_usuario()));
        
        albaran.setFechaHora(LocalDateTime.now());
        albaran.setUsuario(u);
        return albaranRepo.save(albaran);
    }

    @Override
    public Albaran update(Long id, Albaran albaran) {
        Albaran a = findById(id);
        if (albaran.getTipo() != null)
            a.setTipo(albaran.getTipo());
        if (albaran.getObservaciones() != null)
            a.setObservaciones(albaran.getObservaciones());
        if (albaran.getMotivo_merma() != null)
            a.setMotivo_merma(albaran.getMotivo_merma());
        return albaranRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        Albaran a = findById(id);
        
        if (a.getLineas() != null) {
            for (var linea : a.getLineas()) {
                Producto producto = linea.getProducto();
                Double cantidad = linea.getCantidad();
                
                switch (a.getTipo().toLowerCase()) {
                    case "entrada":
                        Double nuevoStock = Math.max(0.0, producto.getCantidad() - cantidad);
                        producto.setCantidad(nuevoStock);
                        break;
                    case "salida":
                    case "merma":
                        producto.setCantidad(producto.getCantidad() + cantidad);
                        break;
                }
            }
        }
        
        albaranRepo.delete(a);
    }

    @Override
    public List<Albaran> findByFecha(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().atTime(23, 59, 59);
        return albaranRepo.findByFechaHoraBetween(inicio, fin);
    }

    @Override
    public void validarAlbaran(Long albaranId) {
        Albaran albaran = findById(albaranId);
        List<LineaAlbaran> lineas = lineaAlbaranRepo.findByAlbaran(albaran);
        
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("El albarán debe tener al menos un producto");
        }
        
        boolean tieneProductosValidos = lineas.stream()
                .anyMatch(linea -> linea.getCantidad() != null && linea.getCantidad() > 0);
        
        if (!tieneProductosValidos) {
            throw new IllegalArgumentException("El albarán debe tener al menos un producto con cantidad mayor a 0");
        }
    }
}

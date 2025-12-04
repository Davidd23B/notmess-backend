package backend.service.impl;

import backend.model.LineaAlbaran;
import backend.model.Albaran;
import backend.model.Producto;
import backend.service.LineaAlbaranService;
import backend.service.StockService;
import backend.repository.LineaAlbaranRepository;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LineaAlbaranServiceImpl implements LineaAlbaranService {

    private final LineaAlbaranRepository lineaAlbaranRepo;
    private final StockService stockService;

    @Override
    public List<LineaAlbaran> findAll() {
        return lineaAlbaranRepo.findAll();
    }

    @Override
    public LineaAlbaran findById(Long id) {
        return lineaAlbaranRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Línea de albarán no encontrada: " + id));
    }

    @Override
    public LineaAlbaran create(LineaAlbaran lineaAlbaran, Albaran albaran, Producto producto) {
        lineaAlbaran.setAlbaran(albaran);
        lineaAlbaran.setProducto(producto);
        List<LineaAlbaran> lineasExistentes = lineaAlbaranRepo.findByAlbaran(albaran);
        for (LineaAlbaran linea : lineasExistentes) {
            if (linea.getProducto().getId_producto().equals(producto.getId_producto())) {
                throw new IllegalArgumentException("El producto ya está agregado a este albarán");
            }
        }
        
        LineaAlbaran saved = lineaAlbaranRepo.save(lineaAlbaran);
        stockService.actualizarStock(saved);
        
        return saved;
    }

    @Override
    public LineaAlbaran update(Long id, LineaAlbaran lineaAlbaran) {
        LineaAlbaran lineaOriginal = findById(id);
        stockService.revertirStock(lineaOriginal);
        if (lineaAlbaran.getCantidad() != null) {
            lineaOriginal.setCantidad(lineaAlbaran.getCantidad());
        }
        LineaAlbaran updated = lineaAlbaranRepo.save(lineaOriginal);
        stockService.actualizarStock(updated);
        return updated;
    }

    @Override
    public void deleteById(Long id) {
        LineaAlbaran linea = findById(id);
        stockService.revertirStock(linea);
        lineaAlbaranRepo.delete(linea);
    }

    @Override
    public List<LineaAlbaran> findByAlbaran(Albaran albaran) {
        return lineaAlbaranRepo.findByAlbaran(albaran);
    }

    @Override
    public List<LineaAlbaran> findByProducto(Producto producto) {
        return lineaAlbaranRepo.findByProducto(producto);
    }
}
package backend.service.impl;

import backend.model.LineaAlbaran;
import backend.model.Producto;
import backend.service.StockService;
import backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductoRepository productoRepository;

    @Override
    public void actualizarStock(LineaAlbaran lineaAlbaran) {
        Producto producto = lineaAlbaran.getProducto();
        String tipoAlbaran = lineaAlbaran.getAlbaran().getTipo();
        Double cantidad = lineaAlbaran.getCantidad();

        if ("entrada".equalsIgnoreCase(tipoAlbaran)) {
            producto.setCantidad(producto.getCantidad() + cantidad);
        } else if ("salida".equalsIgnoreCase(tipoAlbaran) || "merma".equalsIgnoreCase(tipoAlbaran)) {
            if (!verificarStock(lineaAlbaran)) {
                throw new IllegalArgumentException("No hay suficiente stock para realizar la operación");
            }
            producto.setCantidad(producto.getCantidad() - cantidad);
        }
        
        productoRepository.save(producto);
    }

    @Override
    public void revertirStock(LineaAlbaran lineaAlbaran) {
        Producto producto = lineaAlbaran.getProducto();
        String tipoAlbaran = lineaAlbaran.getAlbaran().getTipo();
        Double cantidad = lineaAlbaran.getCantidad();
        if ("entrada".equalsIgnoreCase(tipoAlbaran)) {
            producto.setCantidad(producto.getCantidad() - cantidad);
        } else if ("salida".equalsIgnoreCase(tipoAlbaran) || "merma".equalsIgnoreCase(tipoAlbaran)) {
            producto.setCantidad(producto.getCantidad() + cantidad);
        }
        productoRepository.save(producto);
    }

    @Override
    public boolean verificarStock(LineaAlbaran lineaAlbaran) {
        Producto producto = lineaAlbaran.getProducto();
        String tipoAlbaran = lineaAlbaran.getAlbaran().getTipo();
        Double cantidad = lineaAlbaran.getCantidad();

        if ("salida".equalsIgnoreCase(tipoAlbaran) || "merma".equalsIgnoreCase(tipoAlbaran)) {
            return producto.getCantidad() >= cantidad;
        }
        
        return true;
    }
}
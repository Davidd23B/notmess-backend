package service;

import model.Albaran;
import model.Producto;
import model.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public interface AlbaranService {
    List<Albaran> findAll();
    Albaran findById(Long id);
    Albaran create(Albaran albaran, Usuario usuario, Producto producto);
    void deleteById(Long id);
    List<Albaran> findByFecha(LocalDateTime fecha);
}

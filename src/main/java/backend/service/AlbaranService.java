package backend.service;

import backend.model.Albaran;
import backend.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public interface AlbaranService {
    List<Albaran> findAll();
    Albaran findById(Long id);
    Albaran create(Albaran albaran, Usuario usuario);
    Albaran update(Long id, Albaran albaran);
    void deleteById(Long id);
    List<Albaran> findByFecha(LocalDateTime fecha);
}

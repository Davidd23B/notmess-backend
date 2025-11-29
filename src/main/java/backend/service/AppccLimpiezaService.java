package backend.service;

import backend.model.AppccLimpieza;
import java.util.List;
import java.util.Optional;

public interface AppccLimpiezaService {
    List<AppccLimpieza> findAll();
    AppccLimpieza findById(Long id);
    Optional<AppccLimpieza> findByAppccId(Long idAppcc);
    AppccLimpieza create(AppccLimpieza a);
    AppccLimpieza update(Long id, AppccLimpieza appccLimpieza);
    void deleteById(Long id);
}


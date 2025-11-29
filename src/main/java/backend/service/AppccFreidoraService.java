package backend.service;

import backend.model.AppccFreidora;
import java.util.List;
import java.util.Optional;

public interface AppccFreidoraService {
    List<AppccFreidora> findAll();
    AppccFreidora findById(Long id);
    Optional<AppccFreidora> findByAppccId(Long idAppcc);
    AppccFreidora create(AppccFreidora a);
    AppccFreidora update(Long id, AppccFreidora appccFreidora);
    void deleteById(Long id);
}


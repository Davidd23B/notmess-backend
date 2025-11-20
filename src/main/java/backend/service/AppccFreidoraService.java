package backend.service;

import backend.model.AppccFreidora;
import java.util.List;

public interface AppccFreidoraService {
    List<AppccFreidora> findAll();
    AppccFreidora findById(Long id);
    AppccFreidora create(AppccFreidora a);
    AppccFreidora update(Long id, AppccFreidora appccFreidora);
    void deleteById(Long id);
}


package service;

import model.AppccLimpieza;
import java.util.List;

public interface AppccLimpiezaService {
    List<AppccLimpieza> findAll();
    AppccLimpieza findById(Long id);
    AppccLimpieza create(AppccLimpieza a);
    AppccLimpieza update(Long id, AppccLimpieza appccLimpieza);
    void deleteById(Long id);
}


package backend.service;

import backend.model.AppccTemperatura;
import java.util.List;

public interface AppccTemperaturaService {
    List<AppccTemperatura> findAll();
    AppccTemperatura findById(Long id);
    AppccTemperatura create(AppccTemperatura a);
    AppccTemperatura update(Long id, AppccTemperatura appccTemperatura);
    void deleteById(Long id);
}


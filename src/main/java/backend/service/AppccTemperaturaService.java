package backend.service;

import backend.model.AppccTemperatura;
import java.util.List;
import java.util.Optional;

public interface AppccTemperaturaService {
    List<AppccTemperatura> findAll();
    AppccTemperatura findById(Long id);
    Optional<AppccTemperatura> findByAppccId(Long idAppcc);
    AppccTemperatura create(AppccTemperatura a);
    AppccTemperatura update(Long id, AppccTemperatura appccTemperatura);
    void deleteById(Long id);
}


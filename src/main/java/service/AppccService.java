package service;

import model.Appcc;

import java.time.LocalDateTime;
import java.util.List;

public interface AppccService {
    List<Appcc> findAll();
    Appcc findById(Long id);
    Appcc create(Appcc appcc);
    Appcc update(Long id, Appcc appcc);
    void deleteById(Long id);
    List<Appcc> findByFecha(LocalDateTime fecha);
}

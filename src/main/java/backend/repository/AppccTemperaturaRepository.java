package backend.repository;

import backend.model.AppccTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppccTemperaturaRepository extends JpaRepository<AppccTemperatura, Long> {
}

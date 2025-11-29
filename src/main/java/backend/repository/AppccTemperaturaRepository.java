package backend.repository;

import backend.model.AppccTemperatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppccTemperaturaRepository extends JpaRepository<AppccTemperatura, Long> {
    @Query("SELECT at FROM AppccTemperatura at WHERE at.appcc.id_appcc = :idAppcc")
    Optional<AppccTemperatura> findByAppcc_IdAppcc(@Param("idAppcc") Long idAppcc);
}

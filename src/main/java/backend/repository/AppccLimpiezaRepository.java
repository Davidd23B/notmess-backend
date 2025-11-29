package backend.repository;

import backend.model.AppccLimpieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppccLimpiezaRepository extends JpaRepository<AppccLimpieza, Long> {
    @Query("SELECT al FROM AppccLimpieza al WHERE al.appcc.id_appcc = :idAppcc")
    Optional<AppccLimpieza> findByAppcc_IdAppcc(@Param("idAppcc") Long idAppcc);
}

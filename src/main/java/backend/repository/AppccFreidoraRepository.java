package backend.repository;

import backend.model.AppccFreidora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppccFreidoraRepository extends JpaRepository<AppccFreidora, Long> {
    @Query("SELECT af FROM AppccFreidora af WHERE af.appcc.id_appcc = :idAppcc")
    Optional<AppccFreidora> findByAppcc_IdAppcc(@Param("idAppcc") Long idAppcc);
}

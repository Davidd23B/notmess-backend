package backend.repository;

import backend.model.AppccProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AppccProductoRepository extends JpaRepository<AppccProducto, Long> {
    @Query("SELECT ap FROM AppccProducto ap WHERE ap.appcc.id_appcc = :idAppcc")
    Optional<AppccProducto> findByAppcc_IdAppcc(@Param("idAppcc") Long idAppcc);
}

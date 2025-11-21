package backend.repository;

import backend.model.Appcc;
import backend.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AppccRepository extends JpaRepository<Appcc, Long> {
    List<Appcc> findByUsuario(Usuario usuario);
    Optional<Appcc> findByFechaAndTurno (LocalDateTime fecha, String turno);
    List<Appcc> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    Optional<Appcc> findByFechaAndTurno(LocalDate fecha, String turno);
    boolean existsById(Long id);
}

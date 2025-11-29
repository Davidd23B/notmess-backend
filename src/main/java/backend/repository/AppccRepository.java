package backend.repository;

import backend.model.Appcc;
import backend.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppccRepository extends JpaRepository<Appcc, Long> {
    List<Appcc> findByUsuario(Usuario usuario);
    List<Appcc> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    boolean existsById(Long id);
}

package backend.repository;

import backend.model.Albaran;
import backend.model.Producto;
import backend.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbaranRepository extends JpaRepository<Albaran, Long> {
    List<Albaran> findByUsuario(Usuario usuario);
    List<Albaran> findByProducto(Producto producto);
    List<Albaran> findByTipo(String tipo);
    List<Albaran> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}

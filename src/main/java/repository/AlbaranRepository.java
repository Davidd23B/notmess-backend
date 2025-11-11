package repository;

import model.Albaran;
import model.Producto;
import model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbaranRepository extends JpaRepository<Albaran, Long> {
    List<Albaran> findByUsuario(Usuario usuario);
    List<Albaran> findByProducto(Producto producto);
    List<Albaran> findByTipo(String tipo);
}

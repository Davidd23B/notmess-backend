package repository;

import model.CategoriaProducto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    List<CategoriaProducto> findByNombreContainingIgnoreCase(String nombre);

}

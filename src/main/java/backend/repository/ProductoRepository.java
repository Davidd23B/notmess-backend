package backend.repository;

import backend.model.CategoriaProducto;
import backend.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
    List<Producto> findByCategoria(CategoriaProducto categoria);
    
    @Modifying
    @Query(value = "DELETE FROM producto WHERE id_producto = :id", nativeQuery = true)
    int deleteProductoById(@Param("id") Long id);
}
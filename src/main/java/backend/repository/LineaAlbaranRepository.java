package backend.repository;

import backend.model.LineaAlbaran;
import backend.model.Albaran;
import backend.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaAlbaranRepository extends JpaRepository<LineaAlbaran, Long> {
    List<LineaAlbaran> findByAlbaran(Albaran albaran);
    List<LineaAlbaran> findByProducto(Producto producto);
    
    @Modifying
    @Query("DELETE FROM LineaAlbaran l WHERE l.producto.id_producto = :productoId")
    void deleteByProductoId(@Param("productoId") Long productoId);
}
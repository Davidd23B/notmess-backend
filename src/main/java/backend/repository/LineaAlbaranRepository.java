package backend.repository;

import backend.model.LineaAlbaran;
import backend.model.Albaran;
import backend.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaAlbaranRepository extends JpaRepository<LineaAlbaran, Long> {
    List<LineaAlbaran> findByAlbaran(Albaran albaran);
    List<LineaAlbaran> findByProducto(Producto producto);
    List<LineaAlbaran> findByAlbaranId_albaran(Long id_albaran);
}
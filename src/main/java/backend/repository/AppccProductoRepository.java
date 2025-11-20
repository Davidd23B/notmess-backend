package backend.repository;

import backend.model.AppccProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppccProductoRepository extends JpaRepository<AppccProducto, Long> {
}

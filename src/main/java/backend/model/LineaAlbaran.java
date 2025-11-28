package backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "linea_albaran")
public class LineaAlbaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_linea_albaran;

    @Column(nullable = false)
    private Double cantidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_albaran", nullable = false)
    @JsonBackReference
    private Albaran albaran;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    @JsonBackReference
    private Producto producto;
}
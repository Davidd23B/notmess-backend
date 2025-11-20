package backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appcc_producto")
public class AppccProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_appcc_producto;

    private String estado_producto_congelador1;
    private String estado_producto_congelador2;
    private String estado_producto_congelador3;
    private String estado_producto_camara1;
    private String estado_producto_camara2;
    private String estado_producto_mesa1;
    private String estado_producto_mesa2;
    private String estado_producto_mesa3;

    private Double temperatura_producto_congelador1;
    private Double temperatura_producto_congelador2;
    private Double temperatura_producto_congelador3;
    private Double temperatura_producto_camara1;
    private Double temperatura_producto_camara2;
    private Double temperatura_producto_mesa1;
    private Double temperatura_producto_mesa2;
    private Double temperatura_producto_mesa3;

    private String observaciones;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_appcc", unique = true, nullable = false)
    @JsonBackReference
    private Appcc appcc;
}
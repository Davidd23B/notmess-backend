package model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false, length = 10)
    private String medida; // Unidad, KG o L.

    private String proveedor;

    private String imagen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    @JsonBackReference
    private CategoriaProducto categoria;
}

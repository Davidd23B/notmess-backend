package model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appcc")
public class Appcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_appcc;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fecha;
    private String turno;
    private Boolean completado;
    private String observaciones;

    // Usuario que lo realiza
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    // Subtablas 1:1
    @OneToOne(mappedBy = "appcc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AppccLimpieza appcc_limpieza;

    @OneToOne(mappedBy = "appcc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AppccTemperatura appcc_temperatura;

    @OneToOne(mappedBy = "appcc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AppccProducto appcc_producto;

    @OneToOne(mappedBy = "appcc", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AppccFreidora appcc_freidora;
}
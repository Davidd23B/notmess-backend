package model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appcc_limpieza")
public class AppccLimpieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_appcc_limpieza;

    private Boolean congelador1;
    private Boolean congelador2;
    private Boolean congelador3;
    private Boolean camara1;
    private Boolean camara2;
    private Boolean mesa1;
    private Boolean mesa2;
    private Boolean mesa3;
    private Boolean paredes;
    private Boolean suelo;
    private String observaciones;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_appcc", unique = true, nullable = false)
    @JsonBackReference
    private Appcc appcc;
}
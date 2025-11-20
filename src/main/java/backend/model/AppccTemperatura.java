package backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appcc_temperatura")
public class AppccTemperatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_appcc_temperatura;


    private Double congelador1;
    private Double congelador2;
    private Double congelador3;
    private Double camara1;
    private Double camara2;
    private Double mesa1;
    private Double mesa2;
    private Double mesa3;
    private String observaciones;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_appcc", unique = true, nullable = false)
    @JsonBackReference
    private Appcc appcc;
}
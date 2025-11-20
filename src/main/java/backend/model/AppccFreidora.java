package backend.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appcc_freidora")
public class AppccFreidora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_appcc_freidora;

    private Double temperatura_freidora1;
    private Double temperatura_freidora2;
    private Double tpm_freidora1;
    private Double tpm_freidora2;
    private String observaciones;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_appcc", unique = true, nullable = false)
    @JsonBackReference
    private Appcc appcc;
}

package backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppccTemperaturaDTO {
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
    private Long id_appcc;
}

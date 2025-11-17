package dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppccLimpiezaDTO {
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
    private Long id_appcc;
}

package dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppccFreidoraDTO {
    private Long id_appcc_freidora;
    private Double temperatura_freidora1;
    private Double temperatura_freidora2;
    private Double tpm_freidora1;
    private Double tpm_freidora2;
    private String observaciones;
    private Long id_appcc;
}

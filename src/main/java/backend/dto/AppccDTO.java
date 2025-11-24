package backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppccDTO {
    private Long id_appcc;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime fecha;
    private String turno;
    private Boolean completado;
    private String observaciones;
    private Long id_usuario;
}

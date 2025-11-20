package backend.dto;

import java.time.LocalDateTime;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppccDTO {
    private Long id_appcc;
    private LocalDateTime fecha;
    private String turno;
    private Boolean completado;
    private String observaciones;
    private Long id_usuario;
}

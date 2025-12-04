package backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbaranDTO {
    private Long id_albaran;
    
    @NotNull(message = "El tipo de albaran es requerido")
    @Pattern(regexp = "^(entrada|salida|merma)$", message = "El tipo debe ser 'entrada', 'salida' o 'merma'")
    private String tipo;
    
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime fechaHora;
    private String observaciones;
    private String motivo_merma;
    
    @NotNull(message = "El id del usuario es requerido")
    private Long id_usuario;
    private String nombre_usuario;
}

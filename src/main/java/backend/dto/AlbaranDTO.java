package backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Double cantidad;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime fechaHora;
    private String observaciones;
    private String motivo_merma;
    
    @NotNull(message = "El id del producto es requerido")
    private Long id_producto;
    
    @NotNull(message = "El id del usuario es requerido")
    private Long id_usuario;
}

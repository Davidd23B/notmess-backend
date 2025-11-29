package backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineaAlbaranDTO {
    private Long id_linea_albaran;
    
    @NotNull(message = "La cantidad es requerida")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Double cantidad;
    
    @NotNull(message = "El id del albarán es obligatorio")
    private Long id_albaran;
    
    @NotNull(message = "El id del producto es obligatorio")
    private Long id_producto;
    
    private String nombre_producto;
    
    private String imagen_producto;
}
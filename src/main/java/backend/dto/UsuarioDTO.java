package backend.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id_usuario;
    
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String passwd;
    
    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "^(admin|usuario)$", message = "El rol debe ser 'admin' o 'usuario'")
    private String rol;
    
    private boolean activo;
}

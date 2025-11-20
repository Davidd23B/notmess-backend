package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombre;

    @NotBlank(message = "La contraseña es obligatorio")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String passwd;
}

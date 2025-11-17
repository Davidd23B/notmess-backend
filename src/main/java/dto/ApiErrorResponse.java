package dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * DTO estándar para respuestas de error en la API
 * Proporciona una estructura consistente para todos los errores HTTP
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;
    private String error;
    private String path;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") //Puede que se pueda cambiar el formato a @JsonFormat(shape = Shape.STRING, pattern = "HH:mm:ss dd/MM/yyyy")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

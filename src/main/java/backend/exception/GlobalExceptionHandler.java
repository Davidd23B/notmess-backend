package backend.exception;

import backend.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
            .estado(HttpStatus.NOT_FOUND.value())
            .mensaje(ex.getMessage())
            .error("Recurso no encontrado")
            .ruta(request.getDescription(false).replace("uri=", ""))
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
            .estado(HttpStatus.UNAUTHORIZED.value())
            .mensaje(ex.getMessage())
            .error("No autorizado")
            .ruta(request.getDescription(false).replace("uri=", ""))
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustom(CustomException ex, WebRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
            .estado(HttpStatus.BAD_REQUEST.value())
            .mensaje(ex.getMessage())
            .error("Error en la solicitud")
            .ruta(request.getDescription(false).replace("uri=", ""))
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        String mensaje = ex.getBindingResult().getAllErrors().stream()
            .map(error -> error.getDefaultMessage())
            .findFirst()
            .orElse("Errores de validación");
        
        ApiErrorResponse error = ApiErrorResponse.builder()
            .estado(HttpStatus.BAD_REQUEST.value())
            .mensaje(mensaje)
            .error("Validación fallida")
            .ruta(request.getDescription(false).replace("uri=", ""))
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, WebRequest request) {
        ApiErrorResponse error = ApiErrorResponse.builder()
            .estado(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .mensaje("Error interno del servidor")
            .error(ex.getClass().getSimpleName())
            .ruta(request.getDescription(false).replace("uri=", ""))
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

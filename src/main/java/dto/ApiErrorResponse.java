package dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;
    private String error;
    private String path;
    
    @JsonFormat(pattern = "HH:mm:ss yyyy-MM-dd")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}

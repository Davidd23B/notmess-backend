package backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    // Nota: Para subir/eliminar imágenes de productos específicos, usar ProductoController
    // Este controlador solo se encarga de servir imágenes estáticas

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("imagenes").resolve(filename);
            Resource resource = new UrlResource(imagePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = determinarContentType(filename);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{filename}/info")
    public ResponseEntity<Map<String, Object>> obtenerInfoImagen(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("imagenes").resolve(filename);
            Resource resource = new UrlResource(imagePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                Map<String, Object> info = new HashMap<>();
                info.put("filename", filename);
                info.put("size", resource.contentLength());
                info.put("url", "/api/imagenes/" + filename);
                info.put("exists", true);
                return ResponseEntity.ok(info);
            } else {
                Map<String, Object> info = new HashMap<>();
                info.put("filename", filename);
                info.put("exists", false);
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String determinarContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }
}
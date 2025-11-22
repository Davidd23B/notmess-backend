package backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {
    String createImagen(MultipartFile imagen);
    void deleteImagen(String nombreImagen);
}

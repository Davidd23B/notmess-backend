package backend.service.impl;

import backend.service.ImagenService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ImagenServiceImpl implements ImagenService {
    
    @Value("${imagen.directorio}")
    private String directorio;
    private Path directorioCompleto;

    @Value("${imagen.tam-maximo}")
    private long tamMaximo;

    @Value("${imagen.extensiones}")
    private String extensiones;
    List<String> extensionesPermitidas;

    @Value("${imagen.mimes}")
    private String mimes;
    List<String> mimesPermitidos;

    @PostConstruct
    public void init() {
        directorioCompleto = Path.of(directorio).toAbsolutePath().normalize();
        extensionesPermitidas = List.of(extensiones.toLowerCase().split(","));
        mimesPermitidos = List.of(mimes.toLowerCase().split(","));
        try{
            Files.createDirectories(directorioCompleto);
        }catch(IOException e){
            throw new RuntimeException("No se pudo crear el directorio de imágenes", e);
        }
    }

    @Override
    public String createImagen(MultipartFile imagen) {
        try{
            if(imagen.isEmpty()){
                throw new IllegalArgumentException("La imagen está vacía");
            }
            if(imagen.getSize() > tamMaximo){
                long maxMB = tamMaximo / (1024 * 1024);
                throw new IllegalArgumentException("La imagen es muy grande. Tamaño permitido: " + maxMB + " MB");
            }
            String mime = imagen.getContentType();
            if(mime == null || !mimesPermitidos.contains(mime.toLowerCase())){
                throw new IllegalArgumentException("Tipo de imagen no permitido");
            }
            String original = imagen.getOriginalFilename();
            if(original == null || !original.contains(".")){
                throw new IllegalArgumentException("La imagen no tiene una extensión válida");
            }
            String extension = original.substring(original.lastIndexOf('.')).toLowerCase();
            if(!extensionesPermitidas.contains(extension)){
                throw new IllegalArgumentException("Extensión de imagen no permitida");
            }
            String nombreArchivo = UUID.randomUUID().toString() + extension;
            Path pathDirectorio = directorioCompleto.resolve(nombreArchivo).normalize();
            Files.copy(imagen.getInputStream(), pathDirectorio);
            return nombreArchivo;
        }catch(IOException e){
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }
    
    @Override   
    public void deleteImagen(String nombreImagen) {
        if(nombreImagen == null || nombreImagen.isEmpty() || nombreImagen.isBlank()){
            return;
        }
        try{
            Path pathDirectorio = directorioCompleto.resolve(nombreImagen).normalize();
            Files.deleteIfExists(pathDirectorio);
        }catch(IOException e){}
    }
}

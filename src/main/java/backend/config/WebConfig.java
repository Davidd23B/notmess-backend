package backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${imagen.directorio}")
    private String imagenDirectorio;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path directorioCompleto = Path.of(imagenDirectorio).toAbsolutePath().normalize();
        String ubicacion = "file:" + directorioCompleto.toString() + "/";
        
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(ubicacion)
                .setCachePeriod(3600);
    }
}

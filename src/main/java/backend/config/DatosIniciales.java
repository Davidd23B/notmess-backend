package backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import backend.model.Usuario;
import backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner{

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){
        if(usuarioRepo.findByNombreIgnoreCase("admin").isEmpty()){
            Usuario admin = Usuario.builder()
                .nombre("admin")
                .passwd(passwordEncoder.encode("admin"))
                .rol("admin")
                .activo(true)
                .build();
            usuarioRepo.save(admin);
        }
    }
}

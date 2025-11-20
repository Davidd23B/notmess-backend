package backend.service.impl;

import backend.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.UsuarioRepository;
import backend.service.UsuarioService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> findAll(){
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario findById(Long id){
        return usuarioRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    @Override
    public Usuario create(Usuario usuario){
        if(usuarioRepo.existsByNombreIgnoreCase(usuario.getNombre().trim())){
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        usuario.setNombre(usuario.getNombre().trim());
        usuario.setPasswd(passwordEncoder.encode(usuario.getPasswd()));
        return usuarioRepo.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario){
        Usuario u = findById(id);
        if(usuario.getNombre() != null && !usuario.getNombre().trim().equalsIgnoreCase(u.getNombre())){
            if(usuarioRepo.existsByNombreIgnoreCase(usuario.getNombre().trim())){
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
            u.setNombre(usuario.getNombre().trim());
        }
        if(usuario.getPasswd() != null && !usuario.getPasswd().isEmpty()){
            u.setPasswd(passwordEncoder.encode(usuario.getPasswd()));
        }
        if(usuario.getRol() != null) u.setRol(usuario.getRol());
        return usuarioRepo.save(u);
    }

    @Override
    public void deleteById(Long id){
        Usuario u = usuarioRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
        usuarioRepo.delete(u);
    }
}

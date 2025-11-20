package service.impl;

import dto.LoginRequest;
import dto.RegisterRequest;
import dto.JwtResponse;
import exception.CustomException;
import exception.UnauthorizedException;
import model.Usuario;
import repository.UsuarioRepository;
import security.JwtUtil;
import service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        
        Usuario usuario = usuarioRepo.findByNombreIgnoreCase(loginRequest.getNombre().trim())
            .orElseThrow(() -> {
                return new UnauthorizedException("Usuario o contraseña incorrectos");
            });
        if (!passwordEncoder.matches(loginRequest.getPasswd(), usuario.getPasswd())) {
            throw new UnauthorizedException("Usuario o contraseña incorrectos");
        }
        if (!usuario.isActivo()) {
            throw new UnauthorizedException("Usuario desactivado. Contacta al administrador");
        }
        String token = jwtUtil.generarToken(usuario.getNombre().trim());
        
        return new JwtResponse(token, usuario.getId_usuario(), usuario.getNombre().trim(), usuario.getRol());
    }

    @Override
    public JwtResponse register(RegisterRequest registerRequest) {
        
        if (usuarioRepo.findByNombreIgnoreCase(registerRequest.getNombre().trim()).isPresent()) {
            throw new CustomException("Usuario '" + registerRequest.getNombre() + "' ya existe");
        }
        if (registerRequest.getNombre() == null || registerRequest.getNombre().trim().isEmpty()) {
            throw new CustomException("El nombre de usuario es requerido");
        }
        if (registerRequest.getPasswd() == null || registerRequest.getPasswd().length() < 6) {
            throw new CustomException("La contraseña debe tener al menos 6 caracteres");
        }

        Usuario nuevoUsuario = Usuario.builder()
            .nombre(registerRequest.getNombre().trim())
            .passwd(passwordEncoder.encode(registerRequest.getPasswd()))
            .rol("usuario") // Rol por defecto
            .activo(true)
            .build();
        usuarioRepo.save(nuevoUsuario);
        
        String token = jwtUtil.generarToken(nuevoUsuario.getNombre().trim());
        return new JwtResponse(token, nuevoUsuario.getId_usuario(), nuevoUsuario.getNombre().trim(), nuevoUsuario.getRol());
    }
}

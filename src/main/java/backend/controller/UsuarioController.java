package backend.controller;

import backend.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import backend.service.UsuarioService;
import backend.util.EntityDtoMapper;
import backend.dto.UsuarioDTO;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public List<UsuarioDTO> all() {
        return usuarioService.findAll().stream().map(EntityDtoMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or #id == authentication.principal.id")
    public UsuarioDTO get(@PathVariable Long id) {
        return EntityDtoMapper.toDto(usuarioService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public UsuarioDTO create(@Valid @RequestBody UsuarioDTO dto) {
        Usuario u = Usuario.builder()
                .nombre(dto.getNombre())
                .rol(dto.getRol())
                .activo(dto.isActivo())
                .build();
        Usuario saved = usuarioService.create(u);
        return EntityDtoMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public UsuarioDTO update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        Usuario u = Usuario.builder()
                .nombre(dto.getNombre())
                .rol(dto.getRol())
                .activo(dto.isActivo())
                .build();
        Usuario updated = usuarioService.update(id, u);
        return EntityDtoMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

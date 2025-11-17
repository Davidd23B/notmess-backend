package controller;

import model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.UsuarioService;
import util.EntityDtoMapper;
import dto.UsuarioDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public List<UsuarioDTO> all() {
        return service.findAll().stream().map(EntityDtoMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or #id == authentication.principal.id")
    public UsuarioDTO get(@PathVariable Long id) {
        return EntityDtoMapper.toDto(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public UsuarioDTO create(@Valid @RequestBody UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setRol(dto.getRol());
        u.setActivo(dto.isActivo());
        Usuario saved = service.create(u);
        return EntityDtoMapper.toDto(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public UsuarioDTO update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setRol(dto.getRol());
        u.setActivo(dto.isActivo());
        Usuario updated = service.update(id, u);
        return EntityDtoMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

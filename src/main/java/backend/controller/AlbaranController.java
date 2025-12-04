package backend.controller;

import backend.dto.AlbaranDTO;
import backend.dto.mapper.AlbaranMapper;
import backend.model.Albaran;
import backend.model.Usuario;
import backend.service.AlbaranService;
import backend.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {

    private final AlbaranService albaranService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<AlbaranDTO>> all() {
        List<AlbaranDTO> list = albaranService.findAll().stream()
                .map(AlbaranMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbaranDTO> get(@PathVariable Long id) {
        Albaran albaran = albaranService.findById(id);
        try {
            albaranService.validarAlbaran(id);
        } catch (IllegalArgumentException e) {
            //No hacer nada, esto es solo por validar el albarán.
        }
        return ResponseEntity.ok(AlbaranMapper.toDto(albaran));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AlbaranDTO> create(@Valid @RequestBody AlbaranDTO dto) {
        Albaran albaran = AlbaranMapper.toEntity(dto);
        Usuario usuario = usuarioService.findById(dto.getId_usuario());
        
        Albaran saved = albaranService.create(albaran, usuario);
        return ResponseEntity.ok(AlbaranMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<AlbaranDTO> update(@PathVariable Long id, @Valid @RequestBody AlbaranDTO dto) {
        Albaran albaran = AlbaranMapper.toEntity(dto);
        Albaran updated = albaranService.update(id, albaran);
        return ResponseEntity.ok(AlbaranMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        albaranService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AlbaranDTO>> getByFecha(@PathVariable String fecha) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(fecha + "T00:00:00");
            List<AlbaranDTO> list = albaranService.findByFecha(dateTime).stream()
                    .map(AlbaranMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use: yyyy-MM-dd");
        }
    }

    @PostMapping("/{id}/validar")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> validar(@PathVariable Long id) {
        try {
            albaranService.validarAlbaran(id);
            return ResponseEntity.ok().body(java.util.Map.of("mensaje", "Albarán válido"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }
}

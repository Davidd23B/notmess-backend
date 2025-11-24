package backend.controller;

import backend.dto.AppccDTO;
import backend.dto.mapper.AppccMapper;
import lombok.RequiredArgsConstructor;
import backend.model.Appcc;
import backend.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.UsuarioRepository;
import backend.service.AppccService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc")
public class AppccController {

    private final AppccService appccService;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public ResponseEntity<List<AppccDTO>> all() {
        List<AppccDTO> list = appccService.findAll().stream().map(AppccMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(AppccMapper.toDto(appccService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccDTO> create(@RequestBody AppccDTO dto) {
        Appcc entity = AppccMapper.toEntity(dto);
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            entity.setUsuario(usuario);
        }
        Appcc saved = appccService.create(entity);
        return ResponseEntity.ok(AppccMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccDTO> update(@PathVariable Long id, @RequestBody AppccDTO dto) {
        Appcc a = AppccMapper.toEntity(dto);
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            a.setUsuario(usuario);
        }
        Appcc saved = appccService.update(id, a);
        return ResponseEntity.ok(AppccMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AppccDTO>> getByFecha(@PathVariable String fecha) {
        try {
            java.time.LocalDateTime dateTime = java.time.LocalDateTime.parse(fecha + "T00:00:00");
            List<AppccDTO> list = appccService.findByFecha(dateTime).stream()
                    .map(AppccMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use: yyyy-MM-dd");
        }
    }
}

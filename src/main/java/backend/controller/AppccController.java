package backend.controller;

import backend.dto.AppccDTO;
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
        List<AppccDTO> list = appccService.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(appccService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccDTO> create(@RequestBody AppccDTO dto) {
        Appcc.AppccBuilder builder = Appcc.builder()
                .fecha(dto.getFecha())
                .turno(dto.getTurno())
                .completado(dto.getCompletado())
                .observaciones(dto.getObservaciones());
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            builder.usuario(usuario);
        }
        Appcc saved = appccService.create(builder.build());
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccDTO> update(@PathVariable Long id, @RequestBody AppccDTO dto) {
        Appcc a = Appcc.builder()
                .fecha(dto.getFecha())
                .turno(dto.getTurno())
                .completado(dto.getCompletado())
                .observaciones(dto.getObservaciones())
                .build();
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            a.setUsuario(usuario);
        }
        Appcc saved = appccService.update(id, a);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccDTO toDto(Appcc a) {
        return AppccDTO.builder()
                .id_appcc(a.getId_appcc())
                .fecha(a.getFecha())
                .turno(a.getTurno())
                .completado(a.getCompletado())
                .observaciones(a.getObservaciones())
                .id_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario())
                .build();
    }
}

package controller;

import dto.AppccDTO;
import model.Appcc;
import model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UsuarioRepository;
import service.AppccService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appcc")
public class AppccController {

    private final AppccService service;
    private final UsuarioRepository usuarioRepo;

    public AppccController(AppccService service, UsuarioRepository usuarioRepo) {
        this.service = service;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public ResponseEntity<List<AppccDTO>> all() {
        List<AppccDTO> list = service.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.findById(id)));
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
        Appcc saved = service.create(builder.build());
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
        Appcc saved = service.update(id, a);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
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

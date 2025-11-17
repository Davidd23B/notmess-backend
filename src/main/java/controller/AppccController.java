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
        Appcc a = new Appcc();
        a.setFecha(dto.getFecha());
        a.setTurno(dto.getTurno());
        a.setCompletado(dto.getCompletado());
        a.setObservaciones(dto.getObservaciones());
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            a.setUsuario(usuario);
        }
        Appcc saved = service.create(a);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccDTO> update(@PathVariable Long id, @RequestBody AppccDTO dto) {
        Appcc existing = service.findById(id);
        existing.setFecha(dto.getFecha());
        existing.setTurno(dto.getTurno());
        existing.setCompletado(dto.getCompletado());
        existing.setObservaciones(dto.getObservaciones());
        if (dto.getId_usuario() != null) {
            Usuario usuario = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
            existing.setUsuario(usuario);
        }
        Appcc saved = service.update(id, existing);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccDTO toDto(Appcc a) {
        AppccDTO d = new AppccDTO();
        d.setId_appcc(a.getId_appcc());
        d.setFecha(a.getFecha());
        d.setTurno(a.getTurno());
        d.setCompletado(a.getCompletado());
        d.setObservaciones(a.getObservaciones());
        d.setId_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario());
        return d;
    }
}

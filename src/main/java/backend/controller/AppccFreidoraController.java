package backend.controller;

import backend.dto.AppccFreidoraDTO;
import backend.dto.mapper.AppccFreidoraMapper;
import lombok.RequiredArgsConstructor;
import backend.model.AppccFreidora;
import backend.model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.AppccRepository;
import backend.service.AppccFreidoraService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc/freidora")
public class AppccFreidoraController {

    private final AppccFreidoraService appccFreidoraService;
    private final AppccRepository appccRepo;

    @GetMapping
    public ResponseEntity<List<AppccFreidoraDTO>> all() {
        List<AppccFreidoraDTO> list = appccFreidoraService.findAll().stream().map(AppccFreidoraMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccFreidoraDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(AppccFreidoraMapper.toDto(appccFreidoraService.findById(id)));
    }

    @GetMapping("/appcc/{idAppcc}")
    public ResponseEntity<AppccFreidoraDTO> getByAppccId(@PathVariable Long idAppcc) {
        return appccFreidoraService.findByAppccId(idAppcc)
                .map(AppccFreidoraMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppccFreidoraDTO> create(@RequestBody AppccFreidoraDTO dto) {
        AppccFreidora entity = AppccFreidoraMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccFreidora saved = appccFreidoraService.create(entity);
        return ResponseEntity.ok(AppccFreidoraMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccFreidoraDTO> update(@PathVariable Long id, @RequestBody AppccFreidoraDTO dto) {
        AppccFreidora entity = AppccFreidoraMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccFreidora updated = appccFreidoraService.update(id, entity);
        return ResponseEntity.ok(AppccFreidoraMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccFreidoraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

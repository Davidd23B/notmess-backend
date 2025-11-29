package backend.controller;

import backend.dto.AppccProductoDTO;
import backend.dto.mapper.AppccProductoMapper;
import lombok.RequiredArgsConstructor;
import backend.model.AppccProducto;
import backend.model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.AppccRepository;
import backend.service.AppccProductoService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc/producto")
public class AppccProductoController {

    private final AppccProductoService appccProductoService;
    private final AppccRepository appccRepo;

    @GetMapping
    public ResponseEntity<List<AppccProductoDTO>> all() {
        List<AppccProductoDTO> list = appccProductoService.findAll().stream().map(AppccProductoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccProductoDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(AppccProductoMapper.toDto(appccProductoService.findById(id)));
    }

    @GetMapping("/appcc/{idAppcc}")
    public ResponseEntity<AppccProductoDTO> getByAppccId(@PathVariable Long idAppcc) {
        return appccProductoService.findByAppccId(idAppcc)
                .map(AppccProductoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AppccProductoDTO> create(@RequestBody AppccProductoDTO dto) {
        AppccProducto entity = AppccProductoMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccProducto saved = appccProductoService.create(entity);
        return ResponseEntity.ok(AppccProductoMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccProductoDTO> update(@PathVariable Long id, @RequestBody AppccProductoDTO dto) {
        AppccProducto entity = AppccProductoMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccProducto updated = appccProductoService.update(id, entity);
        return ResponseEntity.ok(AppccProductoMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package backend.controller;

import backend.dto.AppccLimpiezaDTO;
import backend.dto.mapper.AppccLimpiezaMapper;
import lombok.RequiredArgsConstructor;
import backend.model.AppccLimpieza;
import backend.model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.AppccRepository;
import backend.service.AppccLimpiezaService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc/limpieza")
public class AppccLimpiezaController {

    private final AppccLimpiezaService appccLimpiezaService;
    private final AppccRepository appccRepo;

    @GetMapping
    public ResponseEntity<List<AppccLimpiezaDTO>> all() {
        List<AppccLimpiezaDTO> list = appccLimpiezaService.findAll().stream().map(AppccLimpiezaMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccLimpiezaDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(AppccLimpiezaMapper.toDto(appccLimpiezaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccLimpiezaDTO> create(@RequestBody AppccLimpiezaDTO dto) {
        AppccLimpieza entity = AppccLimpiezaMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccLimpieza saved = appccLimpiezaService.create(entity);
        return ResponseEntity.ok(AppccLimpiezaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccLimpiezaDTO> update(@PathVariable Long id, @RequestBody AppccLimpiezaDTO dto) {
        AppccLimpieza entity = AppccLimpiezaMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            entity.setAppcc(appcc);
        }
        AppccLimpieza updated = appccLimpiezaService.update(id, entity);
        return ResponseEntity.ok(AppccLimpiezaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccLimpiezaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

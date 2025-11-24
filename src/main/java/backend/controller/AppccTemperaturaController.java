package backend.controller;

import backend.dto.AppccTemperaturaDTO;
import backend.dto.mapper.AppccTemperaturaMapper;
import lombok.RequiredArgsConstructor;
import backend.model.AppccTemperatura;
import backend.model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.AppccRepository;
import backend.service.AppccTemperaturaService;
import java.util.List;
import java.util.stream.Collectors;
import backend.exception.ResourceNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc/temperatura")
public class AppccTemperaturaController {

    private final AppccTemperaturaService appccTemperaturaService;
    private final AppccRepository appccRepo;

    @GetMapping
    public ResponseEntity<List<AppccTemperaturaDTO>> all() {
        List<AppccTemperaturaDTO> list = appccTemperaturaService.findAll().stream().map(AppccTemperaturaMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccTemperaturaDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(AppccTemperaturaMapper.toDto(appccTemperaturaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccTemperaturaDTO> create(@RequestBody AppccTemperaturaDTO dto) {
        AppccTemperatura entity = AppccTemperaturaMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElseThrow(() -> new ResourceNotFoundException("Appcc no encontrada: " + dto.getId_appcc()));
            entity.setAppcc(appcc);
        }
        AppccTemperatura saved = appccTemperaturaService.create(entity);
        return ResponseEntity.ok(AppccTemperaturaMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppccTemperaturaDTO> update(@PathVariable Long id, @RequestBody AppccTemperaturaDTO dto) {
        AppccTemperatura entity = AppccTemperaturaMapper.toEntity(dto);
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc())
                    .orElseThrow(() -> new ResourceNotFoundException("Appcc no encontrada: " + dto.getId_appcc()));
            entity.setAppcc(appcc);
        }
        AppccTemperatura updated = appccTemperaturaService.update(id, entity);
        return ResponseEntity.ok(AppccTemperaturaMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccTemperaturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

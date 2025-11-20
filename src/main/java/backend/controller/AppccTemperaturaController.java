package backend.controller;

import backend.dto.AppccTemperaturaDTO;
import lombok.RequiredArgsConstructor;
import backend.model.AppccTemperatura;
import backend.model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.repository.AppccRepository;
import backend.service.AppccTemperaturaService;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appcc/temperatura")
public class AppccTemperaturaController {

    private final AppccTemperaturaService appccTemperaturaService;
    private final AppccRepository appccRepo;

    @GetMapping
    public ResponseEntity<List<AppccTemperaturaDTO>> all() {
        List<AppccTemperaturaDTO> list = appccTemperaturaService.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccTemperaturaDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(appccTemperaturaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccTemperaturaDTO> create(@RequestBody AppccTemperaturaDTO dto) {
        AppccTemperatura.AppccTemperaturaBuilder builder = AppccTemperatura.builder()
                .congelador1(dto.getCongelador1())
                .congelador2(dto.getCongelador2())
                .congelador3(dto.getCongelador3())
                .camara1(dto.getCamara1())
                .camara2(dto.getCamara2())
                .mesa1(dto.getMesa1())
                .mesa2(dto.getMesa2())
                .mesa3(dto.getMesa3())
                .observaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            builder.appcc(appcc);
        }
        AppccTemperatura saved = appccTemperaturaService.create(builder.build());
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccTemperaturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccTemperaturaDTO toDto(AppccTemperatura a) {
        return AppccTemperaturaDTO.builder()
                .id_appcc_temperatura(a.getId_appcc_temperatura())
                .congelador1(a.getCongelador1())
                .congelador2(a.getCongelador2())
                .congelador3(a.getCongelador3())
                .camara1(a.getCamara1())
                .camara2(a.getCamara2())
                .mesa1(a.getMesa1())
                .mesa2(a.getMesa2())
                .mesa3(a.getMesa3())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }
}

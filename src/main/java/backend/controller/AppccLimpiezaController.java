package backend.controller;

import backend.dto.AppccLimpiezaDTO;
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
        List<AppccLimpiezaDTO> list = appccLimpiezaService.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccLimpiezaDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(appccLimpiezaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccLimpiezaDTO> create(@RequestBody AppccLimpiezaDTO dto) {
        AppccLimpieza.AppccLimpiezaBuilder builder = AppccLimpieza.builder()
                .congelador1(dto.getCongelador1())
                .congelador2(dto.getCongelador2())
                .congelador3(dto.getCongelador3())
                .camara1(dto.getCamara1())
                .camara2(dto.getCamara2())
                .mesa1(dto.getMesa1())
                .mesa2(dto.getMesa2())
                .mesa3(dto.getMesa3())
                .paredes(dto.getParedes())
                .suelo(dto.getSuelo())
                .observaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            builder.appcc(appcc);
        }
        AppccLimpieza saved = appccLimpiezaService.create(builder.build());
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        appccLimpiezaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccLimpiezaDTO toDto(AppccLimpieza a) {
        return AppccLimpiezaDTO.builder()
                .id_appcc_limpieza(a.getId_appcc_limpieza())
                .congelador1(a.getCongelador1())
                .congelador2(a.getCongelador2())
                .congelador3(a.getCongelador3())
                .camara1(a.getCamara1())
                .camara2(a.getCamara2())
                .mesa1(a.getMesa1())
                .mesa2(a.getMesa2())
                .mesa3(a.getMesa3())
                .paredes(a.getParedes())
                .suelo(a.getSuelo())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }
}

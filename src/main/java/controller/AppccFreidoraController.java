package controller;

import dto.AppccFreidoraDTO;
import model.AppccFreidora;
import model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AppccRepository;
import service.AppccFreidoraService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appcc/freidora")
public class AppccFreidoraController {

    private final AppccFreidoraService service;
    private final AppccRepository appccRepo;

    public AppccFreidoraController(AppccFreidoraService service, AppccRepository appccRepo) {
        this.service = service;
        this.appccRepo = appccRepo;
    }

    @GetMapping
    public ResponseEntity<List<AppccFreidoraDTO>> all() {
        List<AppccFreidoraDTO> list = service.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccFreidoraDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccFreidoraDTO> create(@RequestBody AppccFreidoraDTO dto) {
        AppccFreidora a = new AppccFreidora();
        a.setTemperatura_freidora1(dto.getTemperatura_freidora1());
        a.setTemperatura_freidora2(dto.getTemperatura_freidora2());
        a.setTpm_freidora1(dto.getTpm_freidora1());
        a.setTpm_freidora2(dto.getTpm_freidora2());
        a.setObservaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            a.setAppcc(appcc);
        }
        AppccFreidora saved = service.create(a);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccFreidoraDTO toDto(AppccFreidora a) {
        AppccFreidoraDTO d = new AppccFreidoraDTO();
        d.setId_appcc_freidora(a.getId_appcc_freidora());
        d.setTemperatura_freidora1(a.getTemperatura_freidora1());
        d.setTemperatura_freidora2(a.getTemperatura_freidora2());
        d.setTpm_freidora1(a.getTpm_freidora1());
        d.setTpm_freidora2(a.getTpm_freidora2());
        d.setObservaciones(a.getObservaciones());
        d.setId_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc());
        return d;
    }
}

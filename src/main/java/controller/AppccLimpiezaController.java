package controller;

import dto.AppccLimpiezaDTO;
import model.AppccLimpieza;
import model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AppccRepository;
import service.AppccLimpiezaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appcc/limpieza")
public class AppccLimpiezaController {

    private final AppccLimpiezaService service;
    private final AppccRepository appccRepo;

    public AppccLimpiezaController(AppccLimpiezaService service, AppccRepository appccRepo) {
        this.service = service;
        this.appccRepo = appccRepo;
    }

    @GetMapping
    public ResponseEntity<List<AppccLimpiezaDTO>> all() {
        List<AppccLimpiezaDTO> list = service.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccLimpiezaDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccLimpiezaDTO> create(@RequestBody AppccLimpiezaDTO dto) {
        AppccLimpieza a = new AppccLimpieza();
        a.setCongelador1(dto.getCongelador1());
        a.setCongelador2(dto.getCongelador2());
        a.setCongelador3(dto.getCongelador3());
        a.setCamara1(dto.getCamara1());
        a.setCamara2(dto.getCamara2());
        a.setMesa1(dto.getMesa1());
        a.setMesa2(dto.getMesa2());
        a.setMesa3(dto.getMesa3());
        a.setParedes(dto.getParedes());
        a.setSuelo(dto.getSuelo());
        a.setObservaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            a.setAppcc(appcc);
        }
        AppccLimpieza saved = service.create(a);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccLimpiezaDTO toDto(AppccLimpieza a) {
        AppccLimpiezaDTO d = new AppccLimpiezaDTO();
        d.setId_appcc_limpieza(a.getId_appcc_limpieza());
        d.setCongelador1(a.getCongelador1());
        d.setCongelador2(a.getCongelador2());
        d.setCongelador3(a.getCongelador3());
        d.setCamara1(a.getCamara1());
        d.setCamara2(a.getCamara2());
        d.setMesa1(a.getMesa1());
        d.setMesa2(a.getMesa2());
        d.setMesa3(a.getMesa3());
        d.setParedes(a.getParedes());
        d.setSuelo(a.getSuelo());
        d.setObservaciones(a.getObservaciones());
        d.setId_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc());
        return d;
    }
}

package controller;

import dto.AppccProductoDTO;
import model.AppccProducto;
import model.Appcc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.AppccRepository;
import service.AppccProductoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appcc/producto")
public class AppccProductoController {

    private final AppccProductoService service;
    private final AppccRepository appccRepo;

    public AppccProductoController(AppccProductoService service, AppccRepository appccRepo) {
        this.service = service;
        this.appccRepo = appccRepo;
    }

    @GetMapping
    public ResponseEntity<List<AppccProductoDTO>> all() {
        List<AppccProductoDTO> list = service.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppccProductoDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AppccProductoDTO> create(@RequestBody AppccProductoDTO dto) {
        AppccProducto a = new AppccProducto();
        a.setEstado_producto_congelador1(dto.getEstado_producto_congelador1());
        a.setEstado_producto_congelador2(dto.getEstado_producto_congelador2());
        a.setEstado_producto_congelador3(dto.getEstado_producto_congelador3());
        a.setEstado_producto_camara1(dto.getEstado_producto_camara1());
        a.setEstado_producto_camara2(dto.getEstado_producto_camara2());
        a.setEstado_producto_mesa1(dto.getEstado_producto_mesa1());
        a.setEstado_producto_mesa2(dto.getEstado_producto_mesa2());
        a.setEstado_producto_mesa3(dto.getEstado_producto_mesa3());
        a.setTemperatura_producto_congelador1(dto.getTemperatura_producto_congelador1());
        a.setTemperatura_producto_congelador2(dto.getTemperatura_producto_congelador2());
        a.setTemperatura_producto_congelador3(dto.getTemperatura_producto_congelador3());
        a.setTemperatura_producto_camara1(dto.getTemperatura_producto_camara1());
        a.setTemperatura_producto_camara2(dto.getTemperatura_producto_camara2());
        a.setTemperatura_producto_mesa1(dto.getTemperatura_producto_mesa1());
        a.setTemperatura_producto_mesa2(dto.getTemperatura_producto_mesa2());
        a.setTemperatura_producto_mesa3(dto.getTemperatura_producto_mesa3());
        a.setObservaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            a.setAppcc(appcc);
        }
        AppccProducto saved = service.create(a);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccProductoDTO toDto(AppccProducto a) {
        AppccProductoDTO d = new AppccProductoDTO();
        d.setId_appcc_producto(a.getId_appcc_producto());
        d.setEstado_producto_congelador1(a.getEstado_producto_congelador1());
        d.setEstado_producto_congelador2(a.getEstado_producto_congelador2());
        d.setEstado_producto_congelador3(a.getEstado_producto_congelador3());
        d.setEstado_producto_camara1(a.getEstado_producto_camara1());
        d.setEstado_producto_camara2(a.getEstado_producto_camara2());
        d.setEstado_producto_mesa1(a.getEstado_producto_mesa1());
        d.setEstado_producto_mesa2(a.getEstado_producto_mesa2());
        d.setEstado_producto_mesa3(a.getEstado_producto_mesa3());
        d.setTemperatura_producto_congelador1(a.getTemperatura_producto_congelador1());
        d.setTemperatura_producto_congelador2(a.getTemperatura_producto_congelador2());
        d.setTemperatura_producto_congelador3(a.getTemperatura_producto_congelador3());
        d.setTemperatura_producto_camara1(a.getTemperatura_producto_camara1());
        d.setTemperatura_producto_camara2(a.getTemperatura_producto_camara2());
        d.setTemperatura_producto_mesa1(a.getTemperatura_producto_mesa1());
        d.setTemperatura_producto_mesa2(a.getTemperatura_producto_mesa2());
        d.setTemperatura_producto_mesa3(a.getTemperatura_producto_mesa3());
        d.setObservaciones(a.getObservaciones());
        d.setId_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc());
        return d;
    }
}

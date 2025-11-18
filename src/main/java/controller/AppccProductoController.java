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
        AppccProducto.AppccProductoBuilder builder = AppccProducto.builder()
                .estado_producto_congelador1(dto.getEstado_producto_congelador1())
                .estado_producto_congelador2(dto.getEstado_producto_congelador2())
                .estado_producto_congelador3(dto.getEstado_producto_congelador3())
                .estado_producto_camara1(dto.getEstado_producto_camara1())
                .estado_producto_camara2(dto.getEstado_producto_camara2())
                .estado_producto_mesa1(dto.getEstado_producto_mesa1())
                .estado_producto_mesa2(dto.getEstado_producto_mesa2())
                .estado_producto_mesa3(dto.getEstado_producto_mesa3())
                .temperatura_producto_congelador1(dto.getTemperatura_producto_congelador1())
                .temperatura_producto_congelador2(dto.getTemperatura_producto_congelador2())
                .temperatura_producto_congelador3(dto.getTemperatura_producto_congelador3())
                .temperatura_producto_camara1(dto.getTemperatura_producto_camara1())
                .temperatura_producto_camara2(dto.getTemperatura_producto_camara2())
                .temperatura_producto_mesa1(dto.getTemperatura_producto_mesa1())
                .temperatura_producto_mesa2(dto.getTemperatura_producto_mesa2())
                .temperatura_producto_mesa3(dto.getTemperatura_producto_mesa3())
                .observaciones(dto.getObservaciones());
        if (dto.getId_appcc() != null) {
            Appcc appcc = appccRepo.findById(dto.getId_appcc()).orElse(null);
            builder.appcc(appcc);
        }
        AppccProducto saved = service.create(builder.build());
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AppccProductoDTO toDto(AppccProducto a) {
        return AppccProductoDTO.builder()
                .id_appcc_producto(a.getId_appcc_producto())
                .estado_producto_congelador1(a.getEstado_producto_congelador1())
                .estado_producto_congelador2(a.getEstado_producto_congelador2())
                .estado_producto_congelador3(a.getEstado_producto_congelador3())
                .estado_producto_camara1(a.getEstado_producto_camara1())
                .estado_producto_camara2(a.getEstado_producto_camara2())
                .estado_producto_mesa1(a.getEstado_producto_mesa1())
                .estado_producto_mesa2(a.getEstado_producto_mesa2())
                .estado_producto_mesa3(a.getEstado_producto_mesa3())
                .temperatura_producto_congelador1(a.getTemperatura_producto_congelador1())
                .temperatura_producto_congelador2(a.getTemperatura_producto_congelador2())
                .temperatura_producto_congelador3(a.getTemperatura_producto_congelador3())
                .temperatura_producto_camara1(a.getTemperatura_producto_camara1())
                .temperatura_producto_camara2(a.getTemperatura_producto_camara2())
                .temperatura_producto_mesa1(a.getTemperatura_producto_mesa1())
                .temperatura_producto_mesa2(a.getTemperatura_producto_mesa2())
                .temperatura_producto_mesa3(a.getTemperatura_producto_mesa3())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }
}

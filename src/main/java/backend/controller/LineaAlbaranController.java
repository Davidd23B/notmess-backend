package backend.controller;

import backend.dto.LineaAlbaranDTO;
import backend.dto.mapper.LineaAlbaranMapper;
import backend.model.LineaAlbaran;
import backend.model.Albaran;
import backend.model.Producto;
import backend.service.LineaAlbaranService;
import backend.service.AlbaranService;
import backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lineas-albaran")
public class LineaAlbaranController {

    private final LineaAlbaranService lineaAlbaranService;
    private final AlbaranService albaranService;
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<LineaAlbaranDTO>> all() {
        List<LineaAlbaranDTO> list = lineaAlbaranService.findAll().stream()
                .map(LineaAlbaranMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineaAlbaranDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(LineaAlbaranMapper.toDto(lineaAlbaranService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<LineaAlbaranDTO> create(@Valid @RequestBody LineaAlbaranDTO dto) {
        LineaAlbaran lineaAlbaran = LineaAlbaranMapper.toEntity(dto);
        Albaran albaran = albaranService.findById(dto.getId_albaran());
        Producto producto = productoService.findById(dto.getId_producto());
        
        LineaAlbaran saved = lineaAlbaranService.create(lineaAlbaran, albaran, producto);
        return ResponseEntity.ok(LineaAlbaranMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<LineaAlbaranDTO> update(@PathVariable Long id, @Valid @RequestBody LineaAlbaranDTO dto) {
        LineaAlbaran lineaAlbaran = LineaAlbaranMapper.toEntity(dto);
        LineaAlbaran updated = lineaAlbaranService.update(id, lineaAlbaran);
        return ResponseEntity.ok(LineaAlbaranMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        lineaAlbaranService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/albaran/{albaranId}")
    public ResponseEntity<List<LineaAlbaranDTO>> getByAlbaran(@PathVariable Long albaranId) {
        Albaran albaran = albaranService.findById(albaranId);
        List<LineaAlbaranDTO> lineas = lineaAlbaranService.findByAlbaran(albaran).stream()
                .map(LineaAlbaranMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lineas);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<LineaAlbaranDTO>> getByProducto(@PathVariable Long productoId) {
        Producto producto = productoService.findById(productoId);
        List<LineaAlbaranDTO> lineas = lineaAlbaranService.findByProducto(producto).stream()
                .map(LineaAlbaranMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lineas);
    }
}
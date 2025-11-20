package controller;

import dto.ProductoDTO;
import model.Producto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.ProductoService;
import util.EntityDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> all() {
        List<ProductoDTO> list = productoService.findAll().stream().map(EntityDtoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(EntityDtoMapper.toDto(productoService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ProductoDTO> create(@Valid @RequestBody ProductoDTO dto) {
        Producto saved = productoService.create(EntityDtoMapper.toEntity(dto));
        return ResponseEntity.ok(EntityDtoMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        Producto updated = productoService.update(id, EntityDtoMapper.toEntity(dto));
        return ResponseEntity.ok(EntityDtoMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package controller;

import model.CategoriaProducto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.CategoriaProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaProductoController {

    private final CategoriaProductoService service;

    public CategoriaProductoController(CategoriaProductoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> all() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> get(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<CategoriaProducto> create(@Valid @RequestBody CategoriaProducto c) { return ResponseEntity.ok(service.create(c)); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<CategoriaProducto> update(@PathVariable Long id, @Valid @RequestBody CategoriaProducto c) {
        CategoriaProducto existing = service.findById(id);
        existing.setNombre(c.getNombre());
        return ResponseEntity.ok(service.update(id, existing));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package controller;

import model.CategoriaProducto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import service.CategoriaProductoService;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categorias")
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;


    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> all() { return ResponseEntity.ok(categoriaProductoService.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProducto> get(@PathVariable Long id) { return ResponseEntity.ok(categoriaProductoService.findById(id)); }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<CategoriaProducto> create(@Valid @RequestBody CategoriaProducto c) { return ResponseEntity.ok(categoriaProductoService.create(c)); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<CategoriaProducto> update(@PathVariable Long id, @Valid @RequestBody CategoriaProducto c) {
        CategoriaProducto update = CategoriaProducto.builder()
                .nombre(c.getNombre())
                .build();
        return ResponseEntity.ok(categoriaProductoService.update(id, update));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoriaProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

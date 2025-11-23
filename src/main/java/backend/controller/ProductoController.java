package backend.controller;

import backend.dto.ProductoDTO;
import backend.model.Producto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import backend.service.CsvService;
import backend.service.ImagenService;
import backend.service.ProductoService;
import backend.util.EntityDtoMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ImagenService imagenService;
    private final CsvService csvService;

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

    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ProductoDTO> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String filename = imagenService.createImagen(file);
        Producto update = Producto.builder().imagen(filename).build();
        Producto saved = productoService.update(id, update);
        return ResponseEntity.ok(EntityDtoMapper.toDto(saved));
    }

    @DeleteMapping("/{id}/imagen")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        Producto p = productoService.findById(id);
        if (p.getImagen() != null) {
            imagenService.deleteImagen(p.getImagen());
            Producto update = Producto.builder().imagen(null).build();
            productoService.update(id, update);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exportarCsv")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<byte[]> exportarCsv() {
        List<Producto> productos = productoService.findAll();
        String contenidoCsv = csvService.createProductosCsv(productos);
        byte[] bytesCsv = contenidoCsv.getBytes(StandardCharsets.UTF_8);
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss_ddMMyyyy")).toString();
        String nombreCsv = "productos_" + fecha + ".csv";
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + nombreCsv +"\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(bytesCsv);
    }
}

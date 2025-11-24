package backend.controller;

import backend.dto.AlbaranDTO;
import backend.dto.mapper.AlbaranMapper;
import backend.model.Albaran;
import backend.model.Producto;
import backend.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import backend.repository.ProductoRepository;
import backend.repository.UsuarioRepository;
import backend.service.AlbaranService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {

    private final AlbaranService albaranService;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public List<AlbaranDTO> all() {
        return albaranService.findAll().stream().map(AlbaranMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AlbaranDTO get(@PathVariable Long id) { return AlbaranMapper.toDto(albaranService.findById(id)); }

    @PostMapping
    public AlbaranDTO create(@Valid @RequestBody AlbaranDTO dto) {
        Albaran entity = AlbaranMapper.toEntity(dto);
        Producto p = productoRepo.findById(dto.getId_producto()).orElse(null);
        Usuario u = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
        entity.setProducto(p);
        entity.setUsuario(u);
        Albaran saved = albaranService.create(entity, u, p);
        return AlbaranMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        albaranService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fecha/{fecha}")
    public List<AlbaranDTO> getByFecha(@PathVariable String fecha) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(fecha + "T00:00:00");
            return albaranService.findByFecha(dateTime).stream()
                    .map(AlbaranMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use: yyyy-MM-dd");
        }
    }
}

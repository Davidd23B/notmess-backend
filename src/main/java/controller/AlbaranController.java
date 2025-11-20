package controller;

import dto.AlbaranDTO;
import model.Albaran;
import model.Producto;
import model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repository.ProductoRepository;
import repository.UsuarioRepository;
import service.AlbaranService;
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
        return albaranService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AlbaranDTO get(@PathVariable Long id) { return toDto(albaranService.findById(id)); }

    @PostMapping
    public AlbaranDTO create(@Valid @RequestBody AlbaranDTO dto) {
        Albaran.AlbaranBuilder builder = Albaran.builder()
                .tipo(dto.getTipo())
                .cantidad(dto.getCantidad())
                .fecha_hora(dto.getFecha())
                .observaciones(dto.getObservaciones())
                .motivo_merma(dto.getMotivo_merma());
        Producto p = productoRepo.findById(dto.getId_producto()).orElse(null);
        Usuario u = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
        builder.producto(p).usuario(u);
        Albaran saved = albaranService.create(builder.build(), u, p);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        albaranService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AlbaranDTO toDto(Albaran a) {
        return AlbaranDTO.builder()
                .id_albaran(a.getId_albaran())
                .tipo(a.getTipo())
                .cantidad(a.getCantidad())
                .fecha(a.getFecha_hora())
                .observaciones(a.getObservaciones())
                .motivo_merma(a.getMotivo_merma())
                .id_producto(a.getProducto() == null ? null : a.getProducto().getId_producto())
                .id_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario())
                .build();
    }
}

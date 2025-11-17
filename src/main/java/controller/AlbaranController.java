package controller;

import dto.AlbaranDTO;
import model.Albaran;
import model.Producto;
import model.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repository.ProductoRepository;
import repository.UsuarioRepository;
import service.AlbaranService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/albaranes")
public class AlbaranController {

    private final AlbaranService service;
    private final ProductoRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public AlbaranController(AlbaranService service, ProductoRepository productoRepo, UsuarioRepository usuarioRepo) {
        this.service = service;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public List<AlbaranDTO> all() {
        return service.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AlbaranDTO get(@PathVariable Long id) { return toDto(service.findById(id)); }

    @PostMapping
    public AlbaranDTO create(@Valid @RequestBody AlbaranDTO dto) {
        Albaran a = new Albaran();
        a.setTipo(dto.getTipo());
        a.setCantidad(dto.getCantidad());
        a.setFecha_hora(dto.getFecha());
        a.setObservaciones(dto.getObservaciones());
        a.setMotivo_merma(dto.getMotivo_merma());
        Producto p = productoRepo.findById(dto.getId_producto()).orElse(null);
        Usuario u = usuarioRepo.findById(dto.getId_usuario()).orElse(null);
        a.setProducto(p);
        a.setUsuario(u);
        Albaran saved = service.create(a, u, p);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private AlbaranDTO toDto(Albaran a) {
        AlbaranDTO d = new AlbaranDTO();
        d.setId_albaran(a.getId_albaran());
        d.setTipo(a.getTipo());
        d.setCantidad(a.getCantidad());
        d.setFecha(a.getFecha_hora());
        d.setObservaciones(a.getObservaciones());
        d.setMotivo_merma(a.getMotivo_merma());
        d.setId_producto(a.getProducto() == null ? null : a.getProducto().getId_producto());
        d.setId_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario());
        return d;
    }
}

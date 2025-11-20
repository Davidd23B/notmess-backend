package backend.util;

import backend.dto.ProductoDTO;
import backend.dto.UsuarioDTO;
import backend.model.Producto;
import backend.model.Usuario;

public class EntityDtoMapper {

    public static ProductoDTO toDto(Producto p) {
        if (p == null) return null;
        return ProductoDTO.builder()
                .id_producto(p.getId_producto())
                .nombre(p.getNombre())
                .cantidad(p.getCantidad())
                .medida(p.getMedida())
                .proveedor(p.getProveedor())
                .imagen(p.getImagen())
                .build();
    }

    public static Producto toEntity(ProductoDTO d) {
        if (d == null) return null;
        return Producto.builder()
                .id_producto(d.getId_producto())
                .nombre(d.getNombre())
                .cantidad(d.getCantidad())
                .medida(d.getMedida())
                .proveedor(d.getProveedor())
                .imagen(d.getImagen())
                .build();
    }

    public static UsuarioDTO toDto(Usuario u) {
        if (u == null) return null;
        return UsuarioDTO.builder()
                .id_usuario(u.getId_usuario())
                .nombre(u.getNombre())
                .rol(u.getRol())
                .activo(u.isActivo())
                .build();
    }

}

package backend.dto.mapper;

import backend.dto.ProductoDTO;
import backend.model.Producto;
import backend.model.CategoriaProducto;

public class ProductoMapper {

    public static ProductoDTO toDto(Producto p) {
        if (p == null) return null;
        return ProductoDTO.builder()
                .id_producto(p.getId_producto())
                .id_categoria(p.getCategoria() != null ? p.getCategoria().getId_categoria() : null)
                .nombre(p.getNombre())
                .cantidad(p.getCantidad())
                .medida(p.getMedida())
                .proveedor(p.getProveedor())
                .imagen(p.getImagen())
                .build();
    }

    public static Producto toEntity(ProductoDTO dto) {
        if (dto == null) return null;
        return Producto.builder()
                .categoria(dto.getId_categoria() != null ? CategoriaProducto.builder().id_categoria(dto.getId_categoria()).build() : null)
                .nombre(dto.getNombre())
                .cantidad(dto.getCantidad())
                .medida(dto.getMedida())
                .proveedor(dto.getProveedor())
                .imagen(dto.getImagen())
                .build();
    }
}

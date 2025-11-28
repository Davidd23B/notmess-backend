package backend.dto.mapper;

import backend.dto.LineaAlbaranDTO;
import backend.model.LineaAlbaran;

public class LineaAlbaranMapper {

    public static LineaAlbaranDTO toDto(LineaAlbaran linea) {
        if (linea == null) return null;
        return LineaAlbaranDTO.builder()
                .id_linea_albaran(linea.getId_linea_albaran())
                .cantidad(linea.getCantidad())
                .id_albaran(linea.getAlbaran() == null ? null : linea.getAlbaran().getId_albaran())
                .id_producto(linea.getProducto() == null ? null : linea.getProducto().getId_producto())
                .build();
    }

    public static LineaAlbaran toEntity(LineaAlbaranDTO dto) {
        if (dto == null) return null;
        return LineaAlbaran.builder()
                .cantidad(dto.getCantidad())
                .build();
    }
}
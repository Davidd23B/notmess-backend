package backend.dto.mapper;

import backend.dto.AlbaranDTO;
import backend.model.Albaran;

public class AlbaranMapper {

    public static AlbaranDTO toDto(Albaran a) {
        if (a == null) return null;
        return AlbaranDTO.builder()
                .id_albaran(a.getId_albaran())
                .tipo(a.getTipo())
                .cantidad(a.getCantidad())
                .fechaHora(a.getFechaHora())
                .observaciones(a.getObservaciones())
                .motivo_merma(a.getMotivo_merma())
                .id_producto(a.getProducto() == null ? null : a.getProducto().getId_producto())
                .id_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario())
                .build();
    }

    public static Albaran toEntity(AlbaranDTO dto) {
        if (dto == null) return null;
        return Albaran.builder()
                .tipo(dto.getTipo())
                .cantidad(dto.getCantidad())
                .fechaHora(dto.getFechaHora())
                .observaciones(dto.getObservaciones())
                .motivo_merma(dto.getMotivo_merma())
                .build();
    }
}

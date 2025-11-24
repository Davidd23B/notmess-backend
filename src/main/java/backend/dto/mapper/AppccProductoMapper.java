package backend.dto.mapper;

import backend.dto.AppccProductoDTO;
import backend.model.AppccProducto;

public class AppccProductoMapper {

    public static AppccProductoDTO toDto(AppccProducto a) {
        if (a == null) return null;
        return AppccProductoDTO.builder()
                .id_appcc_producto(a.getId_appcc_producto())
                .estado_producto_congelador1(a.getEstado_producto_congelador1())
                .estado_producto_congelador2(a.getEstado_producto_congelador2())
                .estado_producto_congelador3(a.getEstado_producto_congelador3())
                .estado_producto_camara1(a.getEstado_producto_camara1())
                .estado_producto_camara2(a.getEstado_producto_camara2())
                .estado_producto_mesa1(a.getEstado_producto_mesa1())
                .estado_producto_mesa2(a.getEstado_producto_mesa2())
                .estado_producto_mesa3(a.getEstado_producto_mesa3())
                .temperatura_producto_congelador1(a.getTemperatura_producto_congelador1())
                .temperatura_producto_congelador2(a.getTemperatura_producto_congelador2())
                .temperatura_producto_congelador3(a.getTemperatura_producto_congelador3())
                .temperatura_producto_camara1(a.getTemperatura_producto_camara1())
                .temperatura_producto_camara2(a.getTemperatura_producto_camara2())
                .temperatura_producto_mesa1(a.getTemperatura_producto_mesa1())
                .temperatura_producto_mesa2(a.getTemperatura_producto_mesa2())
                .temperatura_producto_mesa3(a.getTemperatura_producto_mesa3())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }

    public static AppccProducto toEntity(AppccProductoDTO dto) {
        if (dto == null) return null;
        return AppccProducto.builder()
                .estado_producto_congelador1(dto.getEstado_producto_congelador1())
                .estado_producto_congelador2(dto.getEstado_producto_congelador2())
                .estado_producto_congelador3(dto.getEstado_producto_congelador3())
                .estado_producto_camara1(dto.getEstado_producto_camara1())
                .estado_producto_camara2(dto.getEstado_producto_camara2())
                .estado_producto_mesa1(dto.getEstado_producto_mesa1())
                .estado_producto_mesa2(dto.getEstado_producto_mesa2())
                .estado_producto_mesa3(dto.getEstado_producto_mesa3())
                .temperatura_producto_congelador1(dto.getTemperatura_producto_congelador1())
                .temperatura_producto_congelador2(dto.getTemperatura_producto_congelador2())
                .temperatura_producto_congelador3(dto.getTemperatura_producto_congelador3())
                .temperatura_producto_camara1(dto.getTemperatura_producto_camara1())
                .temperatura_producto_camara2(dto.getTemperatura_producto_camara2())
                .temperatura_producto_mesa1(dto.getTemperatura_producto_mesa1())
                .temperatura_producto_mesa2(dto.getTemperatura_producto_mesa2())
                .temperatura_producto_mesa3(dto.getTemperatura_producto_mesa3())
                .observaciones(dto.getObservaciones())
                .build();
    }
}

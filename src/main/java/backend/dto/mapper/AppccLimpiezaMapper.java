package backend.dto.mapper;

import backend.dto.AppccLimpiezaDTO;
import backend.model.AppccLimpieza;

public class AppccLimpiezaMapper {

    public static AppccLimpiezaDTO toDto(AppccLimpieza a) {
        if (a == null) return null;
        return AppccLimpiezaDTO.builder()
                .id_appcc_limpieza(a.getId_appcc_limpieza())
                .congelador1(a.getCongelador1())
                .congelador2(a.getCongelador2())
                .congelador3(a.getCongelador3())
                .camara1(a.getCamara1())
                .camara2(a.getCamara2())
                .mesa1(a.getMesa1())
                .mesa2(a.getMesa2())
                .mesa3(a.getMesa3())
                .paredes(a.getParedes())
                .suelo(a.getSuelo())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }

    public static AppccLimpieza toEntity(AppccLimpiezaDTO dto) {
        if (dto == null) return null;
        return AppccLimpieza.builder()
                .congelador1(dto.getCongelador1())
                .congelador2(dto.getCongelador2())
                .congelador3(dto.getCongelador3())
                .camara1(dto.getCamara1())
                .camara2(dto.getCamara2())
                .mesa1(dto.getMesa1())
                .mesa2(dto.getMesa2())
                .mesa3(dto.getMesa3())
                .paredes(dto.getParedes())
                .suelo(dto.getSuelo())
                .observaciones(dto.getObservaciones())
                .build();
    }
}

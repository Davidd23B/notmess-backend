package backend.dto.mapper;

import backend.dto.AppccTemperaturaDTO;
import backend.model.AppccTemperatura;

public class AppccTemperaturaMapper {

    public static AppccTemperaturaDTO toDto(AppccTemperatura a) {
        if (a == null) return null;
        return AppccTemperaturaDTO.builder()
                .id_appcc_temperatura(a.getId_appcc_temperatura())
                .congelador1(a.getCongelador1())
                .congelador2(a.getCongelador2())
                .congelador3(a.getCongelador3())
                .camara1(a.getCamara1())
                .camara2(a.getCamara2())
                .mesa1(a.getMesa1())
                .mesa2(a.getMesa2())
                .mesa3(a.getMesa3())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }

    public static AppccTemperatura toEntity(AppccTemperaturaDTO dto) {
        if (dto == null) return null;
        return AppccTemperatura.builder()
                .congelador1(dto.getCongelador1())
                .congelador2(dto.getCongelador2())
                .congelador3(dto.getCongelador3())
                .camara1(dto.getCamara1())
                .camara2(dto.getCamara2())
                .mesa1(dto.getMesa1())
                .mesa2(dto.getMesa2())
                .mesa3(dto.getMesa3())
                .observaciones(dto.getObservaciones())
                .build();
    }
}

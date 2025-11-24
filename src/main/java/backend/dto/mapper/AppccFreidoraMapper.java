package backend.dto.mapper;

import backend.dto.AppccFreidoraDTO;
import backend.model.AppccFreidora;

public class AppccFreidoraMapper {

    public static AppccFreidoraDTO toDto(AppccFreidora a) {
        if (a == null) return null;
        return AppccFreidoraDTO.builder()
                .id_appcc_freidora(a.getId_appcc_freidora())
                .temperatura_freidora1(a.getTemperatura_freidora1())
                .temperatura_freidora2(a.getTemperatura_freidora2())
                .tpm_freidora1(a.getTpm_freidora1())
                .tpm_freidora2(a.getTpm_freidora2())
                .observaciones(a.getObservaciones())
                .id_appcc(a.getAppcc() == null ? null : a.getAppcc().getId_appcc())
                .build();
    }

    public static AppccFreidora toEntity(AppccFreidoraDTO dto) {
        if (dto == null) return null;
        return AppccFreidora.builder()
                .temperatura_freidora1(dto.getTemperatura_freidora1())
                .temperatura_freidora2(dto.getTemperatura_freidora2())
                .tpm_freidora1(dto.getTpm_freidora1())
                .tpm_freidora2(dto.getTpm_freidora2())
                .observaciones(dto.getObservaciones())
                .build();
    }
}

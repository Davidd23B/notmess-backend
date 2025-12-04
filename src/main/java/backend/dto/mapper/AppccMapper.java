package backend.dto.mapper;

import backend.dto.AppccDTO;
import backend.model.Appcc;

public class AppccMapper {

    public static AppccDTO toDto(Appcc a) {
        if (a == null) return null;
        return AppccDTO.builder()
                .id_appcc(a.getId_appcc())
                .fecha(a.getFecha())
                .turno(a.getTurno())
                .completado(a.getCompletado())
                .observaciones(a.getObservaciones())
                .id_usuario(a.getUsuario() == null ? null : a.getUsuario().getId_usuario())
                .nombre_usuario(a.getUsuario() == null ? null : a.getUsuario().getNombre())
                .build();
    }

    public static Appcc toEntity(AppccDTO dto) {
        if (dto == null) return null;
        return Appcc.builder()
                .fecha(dto.getFecha())
                .turno(dto.getTurno())
                .completado(dto.getCompletado())
                .observaciones(dto.getObservaciones())
                .build();
    }
}

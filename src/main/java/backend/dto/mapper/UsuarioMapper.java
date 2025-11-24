package backend.dto.mapper;

import backend.dto.UsuarioDTO;
import backend.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDto(Usuario u) {
        if (u == null) return null;
        return UsuarioDTO.builder()
                .id_usuario(u.getId_usuario())
                .nombre(u.getNombre())
                .rol(u.getRol())
                .activo(u.isActivo())
                .build();
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        return Usuario.builder()
                .nombre(dto.getNombre())
                .passwd(dto.getPasswd())
                .rol(dto.getRol())
                .activo(dto.isActivo())
                .build();
    }
}

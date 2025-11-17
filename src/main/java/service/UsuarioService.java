package service;

import model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findById(Long id);
    Usuario create(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    void deleteById(Long id);
}

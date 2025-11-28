package backend.service.impl;

import backend.model.Albaran;
import backend.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AlbaranRepository;
import backend.repository.UsuarioRepository;
import backend.service.AlbaranService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbaranServiceImpl implements AlbaranService {

    private final AlbaranRepository albaranRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public List<Albaran> findAll(){
        return albaranRepo.findAll();
    }

    @Override
    public Albaran findById(Long id){
        return albaranRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Albaran no encontrado: " + id));
    }

    @Override
    public Albaran create(Albaran albaran, Usuario usuario){
        Usuario u = usuarioRepo.findById(usuario.getId_usuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + usuario.getId_usuario()));
        
        albaran.setFechaHora(LocalDateTime.now());
        albaran.setUsuario(u);
        return albaranRepo.save(albaran);
    }

    @Override
    public Albaran update(Long id, Albaran albaran) {
        Albaran a = findById(id);
        if (albaran.getTipo() != null)
            a.setTipo(albaran.getTipo());
        if (albaran.getObservaciones() != null)
            a.setObservaciones(albaran.getObservaciones());
        if (albaran.getMotivo_merma() != null)
            a.setMotivo_merma(albaran.getMotivo_merma());
        return albaranRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        Albaran a = findById(id);
        albaranRepo.delete(a);
    }

    @Override
    public List<Albaran> findByFecha(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().atTime(23, 59, 59);
        return albaranRepo.findByFechaHoraBetween(inicio, fin);
    }
}

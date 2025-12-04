package backend.service.impl;

import backend.model.Appcc;
import backend.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AppccRepository;
import backend.repository.UsuarioRepository;
import backend.service.AppccService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppccServiceImpl implements AppccService {

    private final AppccRepository appccRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public List<Appcc> findAll(){
        return appccRepo.findAll();
    }

    @Override
    public Appcc findById(Long id){
        return appccRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appcc no encontrado: " + id));
    }

    @Override
    public Appcc create(Appcc appcc){
        Usuario u = usuarioRepo.findById(appcc.getUsuario().getId_usuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + appcc.getUsuario().getId_usuario()));
        
        LocalDateTime inicio = appcc.getFecha().toLocalDate().atStartOfDay();
        LocalDateTime fin = appcc.getFecha().toLocalDate().atTime(23, 59, 59);
        List<Appcc> existentes = appccRepo.findByFechaBetween(inicio, fin);
        
        boolean yaExiste = existentes.stream()
            .anyMatch(a -> a.getTurno().equalsIgnoreCase(appcc.getTurno()));
        
        if(yaExiste){
            throw new IllegalArgumentException("Ya existe un registro de APPCC para la fecha y turno indicados");
        }
        
        appcc.setUsuario(u);
        return appccRepo.save(appcc);
    }

    @Override
    public Appcc update(Long id, Appcc appcc){
        Appcc a = findById(id);
        if(appcc.getTurno() != null) a.setTurno(appcc.getTurno());
        if(appcc.getCompletado() != null) a.setCompletado(appcc.getCompletado());
        if(appcc.getObservaciones() != null) a.setObservaciones(appcc.getObservaciones());
        if(appcc.getUsuario() != null){
            Usuario u = usuarioRepo.findById(appcc.getUsuario().getId_usuario()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + appcc.getUsuario().getId_usuario()));
            a.setUsuario(u);
        }
        return appccRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        Appcc a = appccRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appcc no encontrado: " + id));
        appccRepo.delete(a);
    }

    @Override
    public List<Appcc> findByFecha(LocalDateTime fecha){
        LocalDateTime inicio = fecha.toLocalDate().atStartOfDay();
        LocalDateTime fin = fecha.toLocalDate().atTime(23, 59, 59);
        return appccRepo.findByFechaBetween(inicio, fin);
    }
}

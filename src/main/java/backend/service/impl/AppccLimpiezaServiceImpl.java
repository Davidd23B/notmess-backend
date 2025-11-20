package backend.service.impl;

import backend.model.AppccLimpieza;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AppccLimpiezaRepository;
import backend.repository.AppccRepository;
import backend.service.AppccLimpiezaService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppccLimpiezaServiceImpl implements AppccLimpiezaService {

    private final AppccLimpiezaRepository appccLimpiezaRepo;
    private final AppccRepository appccRepo;
    
    @Override
    public List<AppccLimpieza> findAll(){
        return appccLimpiezaRepo.findAll();
    }

    @Override
    public AppccLimpieza findById(Long id) {
        return appccLimpiezaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccLimpieza no encontrada: " + id));
    }

    @Override
    public AppccLimpieza create(AppccLimpieza appccLimpieza) {
        if(!appccRepo.existsById(appccLimpieza.getAppcc().getId_appcc())) {
            throw new ResourceNotFoundException("Appcc no encontrada: " + appccLimpieza.getAppcc().getId_appcc());
        }
        return appccLimpiezaRepo.save(appccLimpieza);
    }

    @Override
    public AppccLimpieza update(Long id, AppccLimpieza appccLimpieza) {
        AppccLimpieza a = findById(id);
        if(appccLimpieza.getCongelador1() != null) a.setCongelador1(appccLimpieza.getCongelador1());
        if(appccLimpieza.getCongelador2() != null) a.setCongelador2(appccLimpieza.getCongelador2());
        if(appccLimpieza.getCongelador3() != null) a.setCongelador3(appccLimpieza.getCongelador3());
        if(appccLimpieza.getCamara1() != null) a.setCamara1(appccLimpieza.getCamara1());
        if(appccLimpieza.getCamara2() != null) a.setCamara2(appccLimpieza.getCamara2());
        if(appccLimpieza.getMesa1() != null) a.setMesa1(appccLimpieza.getMesa1());
        if(appccLimpieza.getMesa2() != null) a.setMesa2(appccLimpieza.getMesa2());
        if(appccLimpieza.getMesa3() != null) a.setMesa3(appccLimpieza.getMesa3());
        if(appccLimpieza.getObservaciones() != null) a.setObservaciones(appccLimpieza.getObservaciones());
        return appccLimpiezaRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        AppccLimpieza a = appccLimpiezaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccLimpieza no encontrada: " + id));
        appccLimpiezaRepo.delete(a);
    }
}


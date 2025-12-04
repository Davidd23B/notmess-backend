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
import java.util.Optional;

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
    public Optional<AppccLimpieza> findByAppccId(Long idAppcc) {
        return appccLimpiezaRepo.findByAppcc_IdAppcc(idAppcc);
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
        a.setCongelador1(appccLimpieza.getCongelador1());
        a.setCongelador2(appccLimpieza.getCongelador2());
        a.setCongelador3(appccLimpieza.getCongelador3());
        a.setCamara1(appccLimpieza.getCamara1());
        a.setCamara2(appccLimpieza.getCamara2());
        a.setMesa1(appccLimpieza.getMesa1());
        a.setMesa2(appccLimpieza.getMesa2());
        a.setMesa3(appccLimpieza.getMesa3());
        a.setParedes(appccLimpieza.getParedes());
        a.setSuelo(appccLimpieza.getSuelo());
        a.setObservaciones(appccLimpieza.getObservaciones());
        return appccLimpiezaRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        AppccLimpieza a = appccLimpiezaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccLimpieza no encontrada: " + id));
        appccLimpiezaRepo.delete(a);
    }
}


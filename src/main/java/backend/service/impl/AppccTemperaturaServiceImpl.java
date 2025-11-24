package backend.service.impl;

import backend.model.AppccTemperatura;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.repository.AppccRepository;
import backend.repository.AppccTemperaturaRepository;
import backend.service.AppccTemperaturaService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppccTemperaturaServiceImpl implements AppccTemperaturaService {

    private final AppccTemperaturaRepository appccTemperaturaRepo;
    private final AppccRepository appccRepo;

    @Override
    public List<AppccTemperatura> findAll(){
        return appccTemperaturaRepo.findAll();
    }

    @Override
    public AppccTemperatura findById(Long id) {
        return appccTemperaturaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccTemperatura no encontrada: " + id));
    }

    @Override
    public AppccTemperatura create(AppccTemperatura appccTemperatura) {
        if(!appccRepo.existsById(appccTemperatura.getAppcc().getId_appcc())) {
            throw new ResourceNotFoundException("Appcc no encontrada: " + appccTemperatura.getAppcc().getId_appcc());
        }
        return appccTemperaturaRepo.save(appccTemperatura);
    }

    @Override
    public AppccTemperatura update(Long id, AppccTemperatura appccTemperatura) {
        AppccTemperatura a = findById(id);
        if(appccTemperatura.getCongelador1() != null) a.setCongelador1(appccTemperatura.getCongelador1());
        if(appccTemperatura.getCongelador2() != null) a.setCongelador2(appccTemperatura.getCongelador2());
        if(appccTemperatura.getCongelador3() != null) a.setCongelador3(appccTemperatura.getCongelador3());
        if(appccTemperatura.getCamara1() != null) a.setCamara1(appccTemperatura.getCamara1());
        if(appccTemperatura.getCamara2() != null) a.setCamara2(appccTemperatura.getCamara2());
        if(appccTemperatura.getMesa1() != null) a.setMesa1(appccTemperatura.getMesa1());
        if(appccTemperatura.getMesa2() != null) a.setMesa2(appccTemperatura.getMesa2());
        if(appccTemperatura.getMesa3() != null) a.setMesa3(appccTemperatura.getMesa3());
        if(appccTemperatura.getObservaciones() != null) a.setObservaciones(appccTemperatura.getObservaciones());
        return appccTemperaturaRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        AppccTemperatura a = appccTemperaturaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccTemperatura no encontrada: " + id));
        appccTemperaturaRepo.delete(a);
    }
}


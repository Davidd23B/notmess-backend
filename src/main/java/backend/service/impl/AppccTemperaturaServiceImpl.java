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
import java.util.Optional;

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
    public Optional<AppccTemperatura> findByAppccId(Long idAppcc) {
        return appccTemperaturaRepo.findByAppcc_IdAppcc(idAppcc);
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
        a.setCongelador1(appccTemperatura.getCongelador1());
        a.setCongelador2(appccTemperatura.getCongelador2());
        a.setCongelador3(appccTemperatura.getCongelador3());
        a.setCamara1(appccTemperatura.getCamara1());
        a.setCamara2(appccTemperatura.getCamara2());
        a.setMesa1(appccTemperatura.getMesa1());
        a.setMesa2(appccTemperatura.getMesa2());
        a.setMesa3(appccTemperatura.getMesa3());
        a.setObservaciones(appccTemperatura.getObservaciones());
        return appccTemperaturaRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        AppccTemperatura a = appccTemperaturaRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccTemperatura no encontrada: " + id));
        appccTemperaturaRepo.delete(a);
    }
}


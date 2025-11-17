package service.impl;

import model.AppccFreidora;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.AppccFreidoraRepository;
import repository.AppccRepository;
import service.AppccFreidoraService;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppccFreidoraServiceImpl implements AppccFreidoraService {

    private final AppccFreidoraRepository appccFreidoraRepo;
    private final AppccRepository appccRepo;

    @Override
    public List<AppccFreidora> findAll(){
        return appccFreidoraRepo.findAll();
    }

    @Override
    public AppccFreidora findById(Long id) {
        return appccFreidoraRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccFreidora no encontrada: " + id));
    }

    @Override
    public AppccFreidora create(AppccFreidora appccFreidora) {
        if(!appccRepo.existsById(appccFreidora.getAppcc().getId_appcc())) {
            throw new ResourceNotFoundException("Appcc no encontrada: " + appccFreidora.getAppcc().getId_appcc());
        }
        return appccFreidoraRepo.save(appccFreidora);
    }

    @Override
    public AppccFreidora update(Long id, AppccFreidora appccFreidora) {
        AppccFreidora a = findById(id);
        if(appccFreidora.getTemperatura_freidora1() != null) a.setTemperatura_freidora1(appccFreidora.getTemperatura_freidora1());
        if(appccFreidora.getTemperatura_freidora2() != null) a.setTemperatura_freidora2(appccFreidora.getTemperatura_freidora2());
        if(appccFreidora.getTpm_freidora1() != null) a.setTpm_freidora1(appccFreidora.getTpm_freidora1());
        if(appccFreidora.getTpm_freidora2() != null) a.setTpm_freidora2(appccFreidora.getTpm_freidora2());
        if(appccFreidora.getObservaciones() != null) a.setObservaciones(appccFreidora.getObservaciones());
        return appccFreidoraRepo.save(a);
    }

    @Override
    public void deleteById(Long id){
        AppccFreidora a = appccFreidoraRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccFreidora no encontrada: " + id));
        appccFreidoraRepo.delete(a);
    }
}


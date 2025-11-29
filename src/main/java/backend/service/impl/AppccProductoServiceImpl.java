package backend.service.impl;

import backend.model.AppccProducto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.repository.AppccProductoRepository;
import backend.repository.AppccRepository;
import backend.service.AppccProductoService;
import backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppccProductoServiceImpl implements AppccProductoService {
    
    private final AppccProductoRepository appccProductoRepo;
    private final AppccRepository appccRepo;

    @Override
    public List<AppccProducto> findAll(){
        return appccProductoRepo.findAll();
    }

    @Override
    public AppccProducto findById(Long id){
        return appccProductoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccProducto no encontrado: " + id));
    }

    @Override
    public Optional<AppccProducto> findByAppccId(Long idAppcc) {
        return appccProductoRepo.findByAppcc_IdAppcc(idAppcc);
    }

    @Override
    public AppccProducto create(AppccProducto appccProducto) {
        if(!appccRepo.existsById(appccProducto.getAppcc().getId_appcc())) {
            throw new ResourceNotFoundException("Appcc no encontrada: " + appccProducto.getAppcc().getId_appcc());
        }
        return appccProductoRepo.save(appccProducto);
    }

    @Override
    public AppccProducto update(Long id, AppccProducto appccProducto) {
        AppccProducto a = findById(id);
        if(appccProducto.getEstado_producto_congelador1() != null) a.setEstado_producto_congelador1(appccProducto.getEstado_producto_congelador1());
        if(appccProducto.getEstado_producto_congelador2() != null) a.setEstado_producto_congelador2(appccProducto.getEstado_producto_congelador2());
        if(appccProducto.getEstado_producto_congelador3() != null) a.setEstado_producto_congelador3(appccProducto.getEstado_producto_congelador3());
        if(appccProducto.getEstado_producto_camara1() != null) a.setEstado_producto_camara1(appccProducto.getEstado_producto_camara1());
        if(appccProducto.getEstado_producto_camara2() != null) a.setEstado_producto_camara2(appccProducto.getEstado_producto_camara2());
        if(appccProducto.getEstado_producto_mesa1() != null) a.setEstado_producto_mesa1(appccProducto.getEstado_producto_mesa1());
        if(appccProducto.getEstado_producto_mesa2() != null) a.setEstado_producto_mesa2(appccProducto.getEstado_producto_mesa2());
        if(appccProducto.getEstado_producto_mesa3() != null) a.setEstado_producto_mesa3(appccProducto.getEstado_producto_mesa3());
        if(appccProducto.getTemperatura_producto_congelador1() != null) a.setTemperatura_producto_congelador1(appccProducto.getTemperatura_producto_congelador1());
        if(appccProducto.getTemperatura_producto_congelador2() != null) a.setTemperatura_producto_congelador2(appccProducto.getTemperatura_producto_congelador2());
        if(appccProducto.getTemperatura_producto_congelador3() != null) a.setTemperatura_producto_congelador3(appccProducto.getTemperatura_producto_congelador3());
        if(appccProducto.getTemperatura_producto_camara1() != null) a.setTemperatura_producto_camara1(appccProducto.getTemperatura_producto_camara1());
        if(appccProducto.getTemperatura_producto_camara2() != null) a.setTemperatura_producto_camara2(appccProducto.getTemperatura_producto_camara2());
        if(appccProducto.getTemperatura_producto_mesa1() != null) a.setTemperatura_producto_mesa1(appccProducto.getTemperatura_producto_mesa1());
        if(appccProducto.getTemperatura_producto_mesa2() != null) a.setTemperatura_producto_mesa2(appccProducto.getTemperatura_producto_mesa2());
        if(appccProducto.getTemperatura_producto_mesa3() != null) a.setTemperatura_producto_mesa3(appccProducto.getTemperatura_producto_mesa3());
        return appccProductoRepo.save(a);
    }
    
    @Override
    public void deleteById(Long id){
        AppccProducto a = appccProductoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("AppccProducto no encontrado: " + id));
        appccProductoRepo.delete(a);
    }
}


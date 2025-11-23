package backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import backend.model.Producto;
import backend.service.CsvService;
import backend.util.CsvUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CsvServiceImpl implements CsvService {

    public String createProductosCsv(List<Producto> productos){
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{
            "ID",
            "Nombre",
            "Cantidad",
            "Medida",
            "Proveedor",
            "Categoría"
        });

        for(Producto p : productos){
            filas.add(new String[]{
                p.getId_producto().toString(),
                p.getNombre(),
                p.getCantidad().toString(),
                p.getMedida(),
                p.getProveedor(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : ""
            });
        }

        return CsvUtil.generarCsv(filas);
    }
}

package backend.service;

import java.util.List;

import backend.model.Producto;

public interface CsvService {
    String createProductosCsv(List<Producto> productos);
}

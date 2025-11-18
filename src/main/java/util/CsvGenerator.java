package util;

import model.Producto;

import java.io.StringWriter;
import java.util.List;

public class CsvGenerator {

    public static String productosToCsv(List<Producto> productos) {
        StringWriter sw = new StringWriter();
        sw.append("id_producto,nombre,cantidad,medida,proveedor,imagen\n");
        for (Producto p : productos) {
            sw.append(String.valueOf(p.getId_producto())).append(',')
              .append(p.getNombre() == null ? "" : p.getNombre()).append(',')
              .append(p.getCantidad() == null ? "" : String.valueOf(p.getCantidad())).append(',')
              .append(p.getMedida() == null ? "" : p.getMedida()).append(',')
              .append(p.getProveedor() == null ? "" : p.getProveedor()).append(',')
              .append(p.getImagen() == null ? "" : p.getImagen()).append('\n');
        }
        return sw.toString();
    }
}

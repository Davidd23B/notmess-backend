package backend.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class CsvUtil {

    public static String generarCsv(List<String[]> filas) {
        try{
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            for(String[] f : filas){
                pw.println(formatoFilas(f));
            }
            pw.flush();
            return sw.toString();
        }catch(Exception e){
            throw new RuntimeException("Error al generar el CSV", e);
        }
    }

    private static String formatoFilas(String[] fila){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < fila.length; i++){
            sb.append(escaparCampo(fila[i]));
            if(i < fila.length - 1){
                sb.append(";");
            }
        }
        return sb.toString();
    }

    private static String escaparCampo(String campo){
        if(campo == null) return "";
        boolean contieneCamposEspeciales = campo.contains(";") || campo.contains("\"") || campo.contains("\n") || campo.contains("\r");
        if(contieneCamposEspeciales){
            campo = campo.replace("\"", "\"\"");
            return "\"" + campo + "\"";
        }
        return campo;
    }
}

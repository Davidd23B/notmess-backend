package backend.util;

public class ValidationUtils {

    private ValidationUtils() {}

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isMinLength(String value, int minLength) {
        return value != null && value.trim().length() >= minLength;
    }

    public static boolean isMaxLength(String value, int maxLength) {
        return value == null || value.length() <= maxLength;
    }

    public static boolean isPositive(Double value) {
        return value != null && value > 0;
    }

    public static boolean isPositiveOrZero(Double value) {
        return value != null && value >= 0;
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return value != null;
    }

    public static boolean isValidRole(String rol) {
        return "admin".equalsIgnoreCase(rol) || "usuario".equalsIgnoreCase(rol);
    }

    public static boolean isValidAlbaranType(String tipo) {
        return "entrada".equalsIgnoreCase(tipo) || 
               "salida".equalsIgnoreCase(tipo) || 
               "merma".equalsIgnoreCase(tipo);
    }

    public static boolean isValidTurno(String turno) {
        return "mañana".equalsIgnoreCase(turno) || "tarde".equalsIgnoreCase(turno);
    }

    public static boolean isValidMedida(String medida) {
        return "unidad".equalsIgnoreCase(medida) || 
               "kg".equalsIgnoreCase(medida) || 
               "L".equalsIgnoreCase(medida);
    }
}

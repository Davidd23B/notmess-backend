package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatTimestamp(LocalDateTime ts) {
        if (ts == null) return null;
        return ts.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}

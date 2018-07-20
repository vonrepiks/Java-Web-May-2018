package utility;

import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {
    private static DateTimeFormatter dateTimeFormatter;

    public static DateTimeFormatter getDecimalFormat() {
        if(dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        }

        return dateTimeFormatter;
    }
}

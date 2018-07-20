package utility;

import java.text.DecimalFormat;

public class DecimalFormatter {
    private static DecimalFormat decimalFormat;

    public static DecimalFormat getDecimalFormat() {
        if(decimalFormat == null) {
            decimalFormat = new DecimalFormat("#.##");
        }

        return decimalFormat;
    }
}

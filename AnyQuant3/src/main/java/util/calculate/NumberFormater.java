package util.calculate;

import java.text.DecimalFormat;

/**
 * Created by kylin on 16/5/24.
 * All rights reserved.
 */
public class NumberFormater {

    private static final DecimalFormat df = new DecimalFormat("#.00");

    public static String formatDouble(double input){
        return  df.format(input);
    }

    public static String formatPercent(double input){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NumberFormater.formatDouble(input));
        stringBuilder.append(NumberFormater.formatDouble('%'));
        return stringBuilder.toString();
    }
}

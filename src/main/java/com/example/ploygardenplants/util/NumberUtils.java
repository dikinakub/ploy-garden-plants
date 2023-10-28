package com.example.ploygardenplants.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class NumberUtils {

    public String formatCurrency(Double amount) {
        DecimalFormat decimalFormat = null;
        try {
            decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(amount);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatCurrency(BigDecimal number) {
        DecimalFormat decimalFormat = null;
        try {
            decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(number);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Format US integer (no floating point)
	 *
     */
    public static String formatInteger(Number number) {
        try {
            return NumberFormat.getInstance(Locale.US).format(number);
        } catch (Exception e) {
            return String.valueOf(number);
        }
    }

}

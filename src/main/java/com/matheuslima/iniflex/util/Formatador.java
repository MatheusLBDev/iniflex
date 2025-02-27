package com.matheuslima.iniflex.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatador {
    public static String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String formatarMoeda(BigDecimal valor) {
        return new DecimalFormat("#,##0.00").format(valor);
    }
}

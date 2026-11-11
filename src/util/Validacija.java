package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Validacija {

    private static final String EPASTA_IZTEIKSME =
            "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    private Validacija() {
    }

    public static boolean navTukss(String teksts) {
        return teksts != null && !teksts.trim().isEmpty();
    }

    public static boolean pareizsEpasts(String epasts) {
        return navTukss(epasts) && epasts.trim().matches(EPASTA_IZTEIKSME);
    }

    public static boolean pareizaParole(String parole) {
        return parole != null && parole.length() >= 4;
    }

    public static Double parseCena(String teksts) {
        if (!navTukss(teksts)) {
            return null;
        }
        try {
            double cena = Double.parseDouble(teksts.trim().replace(',', '.'));
            if (cena > 0) {
                return cena;
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer parsePersonas(String teksts) {
        if (!navTukss(teksts)) {
            return null;
        }
        try {
            int skaits = Integer.parseInt(teksts.trim());
            if (skaits >= 1 && skaits <= 10) {
                return skaits;
            }
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static LocalDate parseDatums(String teksts) {
        if (!navTukss(teksts)) {
            return null;
        }
        String t = teksts.trim();
        String[] formati = {"dd.MM.yyyy", "yyyy-MM-dd"};
        for (String forma : formati) {
            try {
                return LocalDate.parse(t, DateTimeFormatter.ofPattern(forma));
            } catch (DateTimeParseException e) {

            }
        }
        return null;
    }

    public static String datumaTeksts(LocalDate datums) {
        if (datums == null) {
            return "";
        }
        return datums.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static java.util.List<String> statusuSaraksts() {
        java.util.List<String> saraksts = new java.util.ArrayList<>();
        saraksts.add("AKTIVA");
        saraksts.add("APMAKSATA");
        saraksts.add("ATCELTA");
        return saraksts;
    }

    public static boolean derigsStatuss(String statuss) {
        return statusuSaraksts().contains(statuss);
    }
}

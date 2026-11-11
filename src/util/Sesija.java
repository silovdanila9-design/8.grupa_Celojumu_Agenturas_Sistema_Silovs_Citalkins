package util;

import model.Lietotajs;

public final class Sesija {

    public static final int NAV_PIESLEGTS = -1;

    private static int lietotajaId = NAV_PIESLEGTS;

    private static String lietotajvards = "";

    private static String pilnaisVards = "";

    private static String loma = "";

    private Sesija() {
    }

    public static void setAktivaisLietotajs(Lietotajs lietotajs) {
        lietotajaId = lietotajs.getId();
        lietotajvards = lietotajs.getLietotajvards();
        pilnaisVards = lietotajs.getVards() + " " + lietotajs.getUzvards();
        loma = lietotajs.getLoma();
    }

    public static int getLietotajaId() {
        return lietotajaId;
    }

    public static String getLietotajvards() {
        return lietotajvards;
    }

    public static String getPilnaisVards() {
        return pilnaisVards;
    }

    public static String getLoma() {
        return loma;
    }

    public static boolean irPiesledzies() {
        return lietotajaId != NAV_PIESLEGTS;
    }

    public static boolean irAdministrators() {
        return "ADMIN".equals(loma);
    }

    public static void izrakstities() {
        lietotajaId = NAV_PIESLEGTS;
        lietotajvards = "";
        pilnaisVards = "";
        loma = "";
    }
}

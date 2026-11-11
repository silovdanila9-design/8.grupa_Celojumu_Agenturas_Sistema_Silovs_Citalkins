package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String parole) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] baiti = md.digest(parole.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(baiti.length * 2);
            for (byte b : baiti) {
                sb.append(Character.forDigit((b >> 4) & 0xF, 16));
                sb.append(Character.forDigit(b & 0xF, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException("SHA-256 algoritms nav atrasts", e);
        }
    }

    public static boolean parbauditParole(String parole, String hash) {
        if (parole == null || hash == null) {
            return false;
        }
        return hashPassword(parole).equals(hash);
    }
}

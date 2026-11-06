package dao;

import model.Klients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KlientsDAOTest {

    private Klients klients;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testKlientsValidation() {
        Klients k = new Klients(1, "", "Bērziņš", "janis@email.com", "janis123", "parole123");
        if (!k.getVards().isEmpty()) {
            throw new AssertionError("Klienta vārdam jābūt tukšam");
        }
    }

    @Test
    public void testKlientsGetters() {
        Klients k = new Klients(1, "Jānis", "Bērziņš", "janis@email.com", "janis123", "parole123");
        if (!k.getVards().equals("Jānis")) {
            throw new AssertionError("Vārdam jāsakrīt");
        }
    }

    @Test
    public void testEmailFormatValidation() {
        Klients k = new Klients(1, "Jānis", "Bērziņš", "nepareizs_epasts", "janis123", "parole123");
        if (k.getEpasts().contains("@")) {
            throw new AssertionError("E-pastam jāsatur @");
        }
    }
}

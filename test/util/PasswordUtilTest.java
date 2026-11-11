package util;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordUtilTest {

    public PasswordUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHashIrDeterministisks() {
        String h1 = PasswordUtil.hashPassword("parole123");
        String h2 = PasswordUtil.hashPassword("parole123");
        assertEquals("tai pašai parolei tas pats jaucējkods", h1, h2);
    }

    @Test
    public void testHashGarumsUnFormats() {
        String hash = PasswordUtil.hashPassword("vai tad ir buts");
        assertEquals("SHA-256 = 256 biti = 64 heksa zīmes", 64, hash.length());
        assertTrue("tikai heksadecimālas zīmes", hash.matches("[0-9a-f]+"));
    }

    @Test
    public void testHashNavSkaidrsTeksts() {
        assertNotEquals("jaucējkods ≠ parole",
                "parole123", PasswordUtil.hashPassword("parole123"));
    }

    @Test
    public void testDazadasParolesDazadiHashi() {
        String h1 = PasswordUtil.hashPassword("parole1");
        String h2 = PasswordUtil.hashPassword("parole2");
        assertNotEquals("dažādas paroles → dažādi jaucējkodi", h1, h2);
    }

    @Test
    public void testParbauditParole() {
        String hash = PasswordUtil.hashPassword("manaParole1");
        assertTrue("pareizā parole sakrīt", PasswordUtil.parbauditParole("manaParole1", hash));
        assertFalse("nepareizā parole nesakrīt", PasswordUtil.parbauditParole("nepareiza", hash));
        assertFalse("null parole nesakrīt", PasswordUtil.parbauditParole(null, hash));
    }
}

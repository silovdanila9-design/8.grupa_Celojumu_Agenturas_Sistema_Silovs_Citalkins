package util;

import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidacijaTest {

    public ValidacijaTest() {
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
    public void testNavTukss() {
        assertFalse("null nav derīgs ievade", Validacija.navTukss(null));
        assertFalse("tukša virkne nav derīga", Validacija.navTukss(""));
        assertFalse("tikai atstarpes nav derīgas", Validacija.navTukss("   "));
        assertTrue("parasts teksts ir derīgs", Validacija.navTukss("Jānis"));
    }

    @Test
    public void testPareizsEpasts() {
        assertTrue("derīgs e-pasts",
                Validacija.pareizsEpasts("janis.berzins@example.com"));
        assertTrue("derīgs e-pasts ar cipariem",
                Validacija.pareizsEpasts("lietotajs123@inbox.lv"));
        assertFalse("bez @ nav derīgs", Validacija.pareizsEpasts("nepareizs"));
        assertFalse("bez domēna nav derīgs", Validacija.pareizsEpasts("a@b"));
        assertFalse("bez lietotāja nav derīgs",
                Validacija.pareizsEpasts("@example.com"));
        assertFalse("null nav derīgs", Validacija.pareizsEpasts(null));
    }

    @Test
    public void testPareizaParole() {
        assertFalse("3 zīmes ir par īsu", Validacija.pareizaParole("abc"));
        assertTrue("4 zīmes ir pietiekami", Validacija.pareizaParole("abcd"));
        assertFalse("null nav derīgs", Validacija.pareizaParole(null));
    }

    @Test
    public void testParseCena() {
        assertEquals("cena ar punktu", 450.50, Validacija.parseCena("450.50"), 0.001);
        assertEquals("cena ar komatu", 450.50, Validacija.parseCena("450,50"), 0.001);
        assertNull("nulle nav derīga cena", Validacija.parseCena("0"));
        assertNull("negatīva cena nav derīga", Validacija.parseCena("-5"));
        assertNull("teksts nav cena", Validacija.parseCena("abc"));
        assertNull("tukšums nav cena", Validacija.parseCena(""));
    }

    @Test
    public void testParsePersonas() {
        assertEquals("apakšējā robeža 1", Integer.valueOf(1),
                Validacija.parsePersonas("1"));
        assertEquals("augšējā robeža 10", Integer.valueOf(10),
                Validacija.parsePersonas("10"));
        assertNull("0 ir ārpus diapazona", Validacija.parsePersonas("0"));
        assertNull("11 ir ārpus diapazona", Validacija.parsePersonas("11"));
        assertNull("decimālskaitlis nav derīgs", Validacija.parsePersonas("2.5"));
        assertNull("teksts nav derīgs", Validacija.parsePersonas("viss"));
    }

    @Test
    public void testParseDatums() {
        assertEquals("formāts dd.mm.gggg",
                LocalDate.of(2026, 8, 15), Validacija.parseDatums("15.08.2026"));
        assertEquals("formāts gggg-mm-dd",
                LocalDate.of(2026, 8, 15), Validacija.parseDatums("2026-08-15"));
        assertNull("nepastāvošs datums", Validacija.parseDatums("32.13.2026"));
        assertNull("teksts nav datums", Validacija.parseDatums("nākošnedēļ"));
        assertNull("tukšums nav datums", Validacija.parseDatums("  "));
    }

    @Test
    public void testStatusi() {
        assertEquals("trīs atļautie statusi", 3,
                Validacija.statusuSaraksts().size());
        assertTrue("AKTIVA ir derīgs", Validacija.derigsStatuss("AKTIVA"));
        assertTrue("APMAKSATA ir derīgs", Validacija.derigsStatuss("APMAKSATA"));
        assertTrue("ATCELTA ir derīgs", Validacija.derigsStatuss("ATCELTA"));
        assertFalse("izdomāts statuss nav derīgs",
                Validacija.derigsStatuss("NEZINAMS"));
    }
}

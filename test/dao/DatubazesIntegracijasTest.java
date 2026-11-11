package dao;

import db.DatabaseManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import model.Celojums;
import model.Lietotajs;
import model.Maksajums;
import model.Rezervacija;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatubazesIntegracijasTest {

    @BeforeClass
    public static void setUpDatubaze() throws Exception {
        Path pagaiduMape = Files.createTempDirectory("celojumu_testa_db");
        System.setProperty(DatabaseManager.DB_HOME_PROPS,
                pagaiduMape.toAbsolutePath().toString());

        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull("savienojums jāatveras", conn);
        }
    }

    private String unikalsLietotajvards() {
        return "tests" + System.nanoTime();
    }

    private int registretKlientu(String lietotajvards) {
        Lietotajs k = new Lietotajs(0, "Testa", "Klients",
                lietotajvards + "@tests.lv", "20000000", lietotajvards,
                "parole123", "KLIENTS");
        int id = new LietotajsDAO().registret(k);
        assertTrue("reģistrācija jāizdodas (id=" + id + ")", id > 0);
        return id;
    }

    private int izveidotCelojumu(String nosaukums, double cena) {
        Celojums c = new Celojums(0, nosaukums, "Testa galamērķis",
                "Testa apraksts", LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 10), cena, 10);
        int id = new CelojumsDAO().pievienot(c);
        assertTrue("ceļojuma pievienošana jāizdodas", id > 0);
        return id;
    }

    @Test
    public void testAdministratorsVarPieslegties() {
        Lietotajs admin = new LietotajsDAO().pieslegties("admin", "admin123");
        assertNotNull("administrators (admin/admin123) jābūt pieslēdzamam", admin);
        assertEquals("administratora loma ir ADMIN", "ADMIN", admin.getLoma());
    }

    @Test
    public void testRegistracijaUnPieslegsanas() {
        String lv = unikalsLietotajvards();
        int id = registretKlientu(lv);
        assertTrue("jaunais ID jābūt pozitīvam", id > 0);

        Lietotajs piesledzies = new LietotajsDAO().pieslegties(lv, "parole123");
        assertNotNull("pareiziem datiem jāielaiž", piesledzies);
        assertEquals("ID jāsakrīt", id, piesledzies.getId());

        Lietotajs nepareizs = new LietotajsDAO().pieslegties(lv, "nepareiza");
        assertEquals("nepareiza parole nelaiž", null, nepareizs);

        Lietotajs dublēts = new Lietotajs(0, "Vēl", "Viens",
                lv + "2@tests.lv", null, lv, "parole123", "KLIENTS");
        assertEquals("aizņemts lietotājvārds jānoraida", -1,
                new LietotajsDAO().registret(dublēts));
    }

    @Test
    public void testCelojumaCrud() throws SQLException {
        int id = izveidotCelojumu("Testa CRUD ceļojums", 123.45);

        Celojums nolasits = new CelojumsDAO().pecId(id);
        assertNotNull("ceļojums jāatrod pēc ID", nolasits);
        assertEquals("nosaukums jāsakrīt", "Testa CRUD ceļojums",
                nolasits.getNosaukums());
        assertEquals(123.45, nolasits.getCena(), 0.001);

        nolasits.setCena(200.00);
        assertTrue("labošana jāizdodas", new CelojumsDAO().labot(nolasits));
        assertEquals("jaunā cena jāsaglabājas", 200.00,
                new CelojumsDAO().pecId(id).getCena(), 0.001);

        assertTrue("dzēšana jāizdodas", new CelojumsDAO().dzest(id));
        assertEquals("pēc dzēšanas nav atrodams", null,
                new CelojumsDAO().pecId(id));
    }

    @Test
    public void testCelojumuMeklesana() throws SQLException {
        String unikals = "UnikālsMeklesana" + System.nanoTime();
        int id = izveidotCelojumu(unikals, 99.99);

        List<Celojums> atrasti = new CelojumsDAO().mekli(unikals);
        assertEquals("meklēšana pēc unikāla nosaukuma jāatrod tieši 1", 1,
                atrasti.size());
        assertEquals(id, atrasti.get(0).getId());

        assertTrue("dzēšana jāizdodas", new CelojumsDAO().dzest(id));
    }

    @Test
    public void testRezervacijuPiederibaUnStatusi() {
        String lv1 = unikalsLietotajvards();
        String lv2 = unikalsLietotajvards();
        int klients1 = registretKlientu(lv1);
        int klients2 = registretKlientu(lv2);
        int celojumsId = izveidotCelojumu("Testa rezervāciju ceļojums", 100.00);

        Rezervacija r = new Rezervacija(0, klients1, celojumsId, 2,
                200.00, Rezervacija.STATUS_AKTIVA, null);
        int rezId = new RezervacijaDAO().pievienot(r);
        assertTrue("rezervācijas izveide jāizdodas", rezId > 0);

        List<Object[]> sava = new RezervacijaDAO().getTabula(klients1, null);
        assertEquals("klients1 redz tieši 1 savu rezervāciju", 1, sava.size());

        List<Object[]> citas = new RezervacijaDAO().getTabula(klients2, null);
        assertEquals("klients2 neredz citas rezervācijas", 0, citas.size());

        assertTrue("rezervācija pieder klients1",
                new RezervacijaDAO().piederLietotajam(rezId, klients1));
        assertFalse("rezervācija nepieder klients2",
                new RezervacijaDAO().piederLietotajam(rezId, klients2));

        assertTrue("statusa maiņa jāizdodas",
                new RezervacijaDAO().mainitStatusu(rezId,
                        Rezervacija.STATUS_ATCELTA));
        assertEquals("statuss mainīts", Rezervacija.STATUS_ATCELTA,
                new RezervacijaDAO().pecId(rezId).getStatuss());

        new RezervacijaDAO().dzest(rezId);
    }

    @Test
    public void testMaksajumuBiznesaLogika() {
        int klients = registretKlientu(unikalsLietotajvards());
        int celojumsId = izveidotCelojumu("Testa apmaksas ceļojums", 150.00);

        Rezervacija r = new Rezervacija(0, klients, celojumsId, 3,
                450.00, Rezervacija.STATUS_AKTIVA, null);
        int rezId = new RezervacijaDAO().pievienot(r);
        assertTrue("rezervācijas izveide jāizdodas", rezId > 0);

        new MaksajumsDAO().registretArStatusaNovertejumu(
                new Maksajums(0, rezId, 150.00, null, "KARTE"), r);
        assertEquals("apmaksāts 150.00", 150.00,
                new RezervacijaDAO().apmaksataSumma(rezId), 0.001);
        assertEquals("vēl nav pilnībā apmaksāts", Rezervacija.STATUS_AKTIVA,
                new RezervacijaDAO().pecId(rezId).getStatuss());

        new MaksajumsDAO().registretArStatusaNovertejumu(
                new Maksajums(0, rezId, 300.00, null, "PARSKAITIJUMS"), r);
        assertEquals("kopā apmaksāti 450.00", 450.00,
                new RezervacijaDAO().apmaksataSumma(rezId), 0.001);
        assertEquals("pilnīgā apmaksa maina statusu",
                Rezervacija.STATUS_APMAKSATA,
                new RezervacijaDAO().pecId(rezId).getStatuss());

        List<Object[]> maksajumi = new MaksajumsDAO().getTabula(klients);
        assertEquals("abas maksājumu rindas", 2, maksajumi.size());

        new RezervacijaDAO().dzest(rezId);
    }

    @Test
    public void testDzestCelojumuArRezervacijamIrAizliegts() throws SQLException {
        int klients = registretKlientu(unikalsLietotajvards());
        int celojumsId = izveidotCelojumu("Aizsargātais ceļojums", 80.00);

        Rezervacija r = new Rezervacija(0, klients, celojumsId, 1,
                80.00, Rezervacija.STATUS_AKTIVA, null);
        int rezId = new RezervacijaDAO().pievienot(r);
        assertTrue("rezervācijas izveide jāizdodas", rezId > 0);

        try {
            new CelojumsDAO().dzest(celojumsId);
            fail("Datu bāzei JĀBLOĶĒ ceļojuma dzēšana ar aktīvām rezervācijām!");
        } catch (SQLException gaidamakluda) {

        }

        new RezervacijaDAO().dzest(rezId);
        assertTrue(new CelojumsDAO().dzest(celojumsId));
    }

    @Test
    public void testVestureArStatusaFiltru() {
        int klients = registretKlientu(unikalsLietotajvards());
        int celojumsId = izveidotCelojumu("Testa vēstures ceļojums", 50.00);

        Rezervacija r = new Rezervacija(0, klients, celojumsId, 2,
                100.00, Rezervacija.STATUS_AKTIVA, null);
        int rezId = new RezervacijaDAO().pievienot(r);
        new RezervacijaDAO().mainitStatusu(rezId, Rezervacija.STATUS_ATCELTA);

        List<Object[]> atceltas = new RezervacijaDAO().getVesture(klients,
                "ATCELTA");
        assertEquals("vēsturē ar filtru ATCELTA tieši 1 rinda", 1,
                atceltas.size());
        assertEquals("apmaksātā summa ir 0.00", 0.0,
                (Double) atceltas.get(0)[6], 0.001);

        List<Object[]> apmaksatas = new RezervacijaDAO().getVesture(klients,
                "APMAKSATA");
        assertEquals("ar filtru APMAKSATA nav rindu", 0, apmaksatas.size());

        new RezervacijaDAO().dzest(rezId);
    }
}

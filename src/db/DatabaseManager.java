package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.PasswordUtil;

public class DatabaseManager {

    public static final String DB_HOME_PROPS = "celojumu.db.home";

    public static String datubazesMape() {
        return System.getProperty(DB_HOME_PROPS,
                System.getProperty("user.home") + File.separator
                        + "CelojumuAgenturaDB");
    }

    public static String savienojumaUrl() {
        return "jdbc:derby:" + datubazesMape() + File.separator
                + "agenturas_db" + ";create=true";
    }

    public static Connection getConnection() throws SQLException {
        try {

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC draiveris nav atrasts! "
                    + "Pārbaudiet, vai projekta lib mapē ir derby.jar");
        }

        String url = savienojumaUrl();
        Connection conn;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {

            if (irNesaderibasKluda(ex)) {
                System.err.println("Datu bāzes formāts nav saderīgs — "
                        + "izveidoju jaunu datu bāzi: " + url);
                dzestDatubazesMapi();
                conn = DriverManager.getConnection(url);
            } else {
                throw ex;
            }
        }

        izveidotStrukturu(conn);
        return conn;
    }

    private static boolean irNesaderibasKluda(SQLException ex) {
        SQLException tekosais = ex;
        while (tekosais != null) {
            String sqlState = tekosais.getSQLState();
            if (sqlState != null && (sqlState.startsWith("XSLA")
                    || sqlState.startsWith("XJ04"))) {
                return true;
            }
            tekosais = tekosais.getNextException();
        }
        return false;
    }

    private static void dzestDatubazesMapi() {
        File mape = new File(datubazesMape());
        if (mape.exists()) {
            dzestRekursivi(mape);
            System.err.println("Vecā datu bāze izdzēsta: " + mape);
        }
    }

    private static void dzestRekursivi(File fails) {
        File[] apakš = fails.listFiles();
        if (apakš != null) {
            for (File a : apakš) {
                dzestRekursivi(a);
            }
        }
        fails.delete();
    }

    private static void izveidotStrukturu(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            izpilditJaNav(stmt,
                    "CREATE TABLE LIETOTAJS ("
                    + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                    + "(START WITH 1, INCREMENT BY 1), "
                    + "VARDS VARCHAR(50) NOT NULL, "
                    + "UZVARDS VARCHAR(50) NOT NULL, "
                    + "EPASTS VARCHAR(100) NOT NULL, "
                    + "TALRUNIS VARCHAR(20), "
                    + "LIETOTAJVARDS VARCHAR(30) NOT NULL, "
                    + "PAROLES_HASH VARCHAR(64) NOT NULL, "
                    + "LOMA VARCHAR(10) NOT NULL DEFAULT 'KLIENTS', "
                    + "REG_DATUMS TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "CONSTRAINT PK_LIETOTAJS PRIMARY KEY (ID), "
                    + "CONSTRAINT UQ_LIETOTAJS_LIETOTAJVARDS UNIQUE (LIETOTAJVARDS), "
                    + "CONSTRAINT CK_LIETOTAJS_LOMA CHECK (LOMA IN ('KLIENTS','ADMIN')))");

            izpilditJaNav(stmt,
                    "CREATE TABLE CELOJUMS ("
                    + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                    + "(START WITH 1, INCREMENT BY 1), "
                    + "NOSAUKUMS VARCHAR(100) NOT NULL, "
                    + "GALAMERKIS VARCHAR(60) NOT NULL, "
                    + "APRAKSTS VARCHAR(500), "
                    + "SAKUMA_DATUMS DATE NOT NULL, "
                    + "BEIGU_DATUMS DATE NOT NULL, "
                    + "CENA DECIMAL(10,2) NOT NULL, "
                    + "VIETU_SKAITS INT NOT NULL, "
                    + "CONSTRAINT PK_CELOJUMS PRIMARY KEY (ID), "
                    + "CONSTRAINT CK_CELOJUMS_CENA CHECK (CENA > 0), "
                    + "CONSTRAINT CK_CELOJUMS_VIETAS CHECK (VIETU_SKAITS > 0), "
                    + "CONSTRAINT CK_CELOJUMS_DATUMI CHECK (BEIGU_DATUMS >= SAKUMA_DATUMS))");

            izpilditJaNav(stmt,
                    "CREATE TABLE REZERVACIJA ("
                    + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                    + "(START WITH 1, INCREMENT BY 1), "
                    + "LIETOTAJS_ID INT NOT NULL, "
                    + "CELOJUMS_ID INT NOT NULL, "
                    + "PERSONU_SKAITS INT NOT NULL, "
                    + "KOPSUMMA DECIMAL(10,2) NOT NULL, "
                    + "STATUSS VARCHAR(12) NOT NULL DEFAULT 'AKTIVA', "
                    + "IZVEIDOTA TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "CONSTRAINT PK_REZERVACIJA PRIMARY KEY (ID), "
                    + "CONSTRAINT FK_REZERVACIJA_LIETOTAJS FOREIGN KEY (LIETOTAJS_ID) "
                    + "REFERENCES LIETOTAJS (ID) ON DELETE CASCADE, "
                    + "CONSTRAINT FK_REZERVACIJA_CELOJUMS FOREIGN KEY (CELOJUMS_ID) "
                    + "REFERENCES CELOJUMS (ID) ON DELETE RESTRICT, "
                    + "CONSTRAINT CK_REZERVACIJA_PERSONAS CHECK (PERSONU_SKAITS BETWEEN 1 AND 10), "
                    + "CONSTRAINT CK_REZERVACIJA_STATUSS CHECK (STATUSS IN ('AKTIVA','APMAKSATA','ATCELTA')))");

            izpilditJaNav(stmt,
                    "CREATE TABLE MAKSAJUMS ("
                    + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY "
                    + "(START WITH 1, INCREMENT BY 1), "
                    + "REZERVACIJA_ID INT NOT NULL, "
                    + "SUMMA DECIMAL(10,2) NOT NULL, "
                    + "DATUMS TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                    + "VEIDS VARCHAR(15) NOT NULL, "
                    + "CONSTRAINT PK_MAKSAJUMS PRIMARY KEY (ID), "
                    + "CONSTRAINT FK_MAKSAJUMS_REZERVACIJA FOREIGN KEY (REZERVACIJA_ID) "
                    + "REFERENCES REZERVACIJA (ID) ON DELETE CASCADE, "
                    + "CONSTRAINT CK_MAKSAJUMS_SUMMA CHECK (SUMMA > 0), "
                    + "CONSTRAINT CK_MAKSAJUMS_VEIDS CHECK (VEIDS IN ('KARTE','PARSKAITIJUMS','SKAIDRA_NAUDA')))");

            izpilditJaNav(stmt,
                    "CREATE INDEX IX_REZERVACIJA_LIETOTAJS ON REZERVACIJA (LIETOTAJS_ID)");
            izpilditJaNav(stmt,
                    "CREATE INDEX IX_MAKSAJUMS_REZERVACIJA ON MAKSAJUMS (REZERVACIJA_ID)");

            iesetSakumaDatus(stmt);
        }
    }

    private static void izpilditJaNav(Statement stmt, String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) {
                System.err.println("Brīdinājums izpildot priekšteci: "
                        + e.getMessage());
            }
        }
    }

    private static void iesetSakumaDatus(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM LIETOTAJS");
        rs.next();
        if (rs.getInt(1) == 0) {
            String adminHash = PasswordUtil.hashPassword("admin123");
            stmt.executeUpdate(
                    "INSERT INTO LIETOTAJS (VARDS, UZVARDS, EPASTS, TALRUNIS, "
                    + "LIETOTAJVARDS, PAROLES_HASH, LOMA) VALUES "
                    + "('Sistēmas', 'Administrators', 'admin@celojumaagentura.lv', "
                    + "'+371 20000000', 'admin', '" + adminHash + "', 'ADMIN')");
            System.out.println("Izveidots administrators: admin / admin123");
        }
        rs.close();

        rs = stmt.executeQuery("SELECT COUNT(*) FROM CELOJUMS");
        rs.next();
        if (rs.getInt(1) == 0) {
            String[][] piedavajumi = {
                {"Atpūta Spānijā", "Barselona", "Saulaina nedēļa Barselonā ar viesnīcu", "450.00"},
                {"Kalnu slēpošana", "Insbruka", "Aktīva atpūta Austrijas Alpos", "600.00"},
                {"Ekskursija Romā", "Roma", "Vēsturiskā tūre pa Itālijas galvaspilsētu", "350.00"},
                {"Tropu paradīze", "Maldīvi", "Atpūta Maldīvu salu bungalo pie okeāna", "1200.00"},
                {"Grezns kruīzs", "Vidusjūra", "Vidusjūras kruīzs ar visiem labumiem", "950.00"},
                {"Parīzes romantika", "Parīze", "Nedēļas nogale Francijas galvaspilsētā", "400.00"},
                {"Atrandēšanās Norvēģijā", "Bergena", "Fjordu apskate un pārgājieni", "780.00"},
                {"Eksotiskā Taizeme", "Bankoka", "Bankokas un Puketas pludmales", "890.00"},
                {"Grieķijas salas", "Santorīni", "Atpūta Santorīni un Mikonos salās", "670.00"},
                {"Prāgas arhitektūra", "Prāga", "Vēsturiskā Čehijas galvaspilsēta", "250.00"},
                {"Ēģiptes piramīdas", "Hurgada", "Kūrorts Hurgadā un ekskursijas", "520.00"},
                {"Londona un Lielbritānija", "Londona", "Muzeji, teātri un pilsētas iepazīšana", "480.00"},
                {"Portugāles saule", "Lisabona", "Lisabona un Porto vīna dārzi", "590.00"},
                {"Japānas tradīcijas", "Tokija", "Tokija un Kioto rudens noskaņās", "1500.00"},
                {"Turcijas all-inclusive", "Antālija", "Antālijas piekrastes viesnīca", "430.00"},
                {"Islande - ledāji un geizeri", "Reikjavika", "Ziemeļblāzmas medības un dabas brīnumi", "850.00"},
                {"Kipras saule", "Limasola", "Atpūta Vidusjūras salā", "510.00"},
                {"Horvātijas pludmales", "Dubrovnika", "Dubrovnika un Adrijas jūras krasti", "620.00"}
            };

            java.time.LocalDate sodien = java.time.LocalDate.now();
            for (int i = 0; i < piedavajumi.length; i++) {
                java.time.LocalDate sakums = sodien.plusDays(30 + i * 10);
                java.time.LocalDate beigas = sakums.plusDays(7 + (i % 3) * 3);
                stmt.executeUpdate(
                        "INSERT INTO CELOJUMS (NOSAUKUMS, GALAMERKIS, APRAKSTS, "
                        + "SAKUMA_DATUMS, BEIGU_DATUMS, CENA, VIETU_SKAITS) VALUES ("
                        + "'" + piedavajumi[i][0].replace("'", "''") + "', "
                        + "'" + piedavajumi[i][1].replace("'", "''") + "', "
                        + "'" + piedavajumi[i][2].replace("'", "''") + "', "
                        + "{d '" + sakums + "'}, "
                        + "{d '" + beigas + "'}, "
                        + piedavajumi[i][3] + ", "
                        + (15 + (i % 4) * 5) + ")");
            }
            System.out.println("Ievietoti " + piedavajumi.length
                    + " sākuma ceļojumi katalogā.");
        }
        rs.close();
    }

    public static void aizvertDatubazi() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                System.out.println("Derby datu bāze kārtīgi aizvērta.");
            } else {
                System.err.println("Brīdinājums aizverot datu bāzi: "
                        + e.getMessage());
            }
        }
    }
}

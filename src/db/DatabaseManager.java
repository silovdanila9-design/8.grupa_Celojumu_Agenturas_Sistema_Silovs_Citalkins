/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Danils.Silovs
 */
public class DatabaseManager {
    private static final String DB_PATH = System.getProperty("user.home") + "/CelojumuAgenturaDB/celojums_db";
    private static final String URL = "jdbc:derby:" + DB_PATH + ";create=true";

    /**
     * Atgriež savienojumu ar Derby datubāzi.
     *
     * Ja datubāze eksistē, bet tās formāts nav saderīgs ar pašreizējo Derby
     * versiju (kļūda XSLAN), automātiski izdzēš veco datubāzi un izveido jaunu.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC draiveris nav atrasts!");
            e.printStackTrace();
        }

        Connection conn;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            // Pārbaudām, vai kļūda ir versiju nesaderība (XSLAN) vai arī
            // datubāze jau eksistē, bet nevar tikt atvērta (XJ040 + XSLAN).
            if (isIncompatibleDatabaseError(ex)) {
                System.err.println("Datubāzes formāts nav saderīgs ar pašreizējo Derby versiju.");
                System.err.println("Dzēšam veco datubāzi un izveidojam jaunu: " + DB_PATH);
                deleteDatabaseFolder();
                // Mēģinām vēlreiz - tagad Derby izveidos jaunu datubāzi
                conn = DriverManager.getConnection(URL);
            } else {
                throw ex;
            }
        }

        initializeDatabase(conn);

        return conn;
    }

    /**
     * Pārbauda, vai SQLException ir versiju nesaderības kļūda (XSLAN)
     * vai datubāzes startēšanas kļūda (XJ040), ko izraisa XSLAN.
     */
    private static boolean isIncompatibleDatabaseError(SQLException ex) {
        SQLException current = ex;
        while (current != null) {
            String sqlState = current.getSQLState();
            if (sqlState != null && (sqlState.startsWith("XSLA") || sqlState.startsWith("XJ04"))) {
                return true;
            }
            current = current.getNextException();
        }
        return false;
    }

    /**
     * Dzēš Derby datubāzes mapi rekursīvi.
     * Derby ir jābūt aizvērtai, pirms dzēš mapi.
     */
    private static void deleteDatabaseFolder() {
        File dbDir = new File(DB_PATH);
        if (!dbDir.exists()) {
            return;
        }
        // Vispirms dzēšam "db.lck" failu, kas varētu traucēt
        // (parasti Derby jau ir aizvērusi savienojumu, bet drošības pēc)
        try {
            deleteRecursive(dbDir);
            System.out.println("Vecā datubāze veiksmīgi izdzēsta.");
        } catch (Exception e) {
            System.err.println("Neizdevās izdzēst veco datubāzi: " + e.getMessage());
            System.err.println("Lūdzu, dzēsiet mapi manuāli: " + DB_PATH);
        }
    }

    /**
     * Rekursīvi dzēš failu vai mapi.
     */
    private static void deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursive(child);
                }
            }
        }
        file.delete();
    }

    private static void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {

            String createUsersTable = "CREATE TABLE LIETOTAJS (" +
                    "ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "VARDS VARCHAR(50), " +
                    "UZVARDS VARCHAR(50), " +
                    "EPASTS VARCHAR(100), " +
                    "LIETOTAJAVRDS VARCHAR(50) UNIQUE, " +
                    "PAROLES_JAUCENKODS VARCHAR(100))";
            try {
                stmt.executeUpdate(createUsersTable);
            } catch (SQLException e) {
            }

            String createRezervacijaTable = "CREATE TABLE REZERVACIJA (" +
                    "ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "CELOJUMS_ID INT, " +
                    "LIETOTAJVARDS VARCHAR(50))";
            try {
                stmt.executeUpdate(createRezervacijaTable);
            } catch (SQLException e) {
            }

            String createCelojumiTable = "CREATE TABLE CELOJUMS (" +
                    "ID INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, " +
                    "NOSAUKUMS VARCHAR(100), " +
                    "APRAKSTS VARCHAR(255), " +
                    "CENA DOUBLE)";
            try {
                stmt.executeUpdate(createCelojumiTable);

                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Atpūta Spānijā', 'Saulaina nedēļa Barselonā ar viesnīcu', 450.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Kalnu slēpošana', 'Aktīva atpūta Austrijas alpos', 600.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Ekskursija Romā', 'Vēsturiskā tūre pa Itālijas galvaspilsētu', 350.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Tropu paradīze', 'Atpūta Maldīvu salu bungalo pie okeāna', 1200.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Grezns kruīzs', 'Vidusjūras kruīzs ar visiem labumiem', 950.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Parīzes romantika', 'Nedēļas nogale Francijas galvaspilsētā', 400.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Atrandēšanās Norvēģijā', 'Fjordu apskate un pārgājieni', 780.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Eksotiskā Taizeme', 'Bankoka un Puketas pludmales', 890.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Grieķijas salas', 'Atpūta Santorinā un Mikonā', 670.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Prāgas arhitektūra', 'Vēsturiskā Čehijas galvaspilsēta', 250.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Ēģiptes piramīdas', 'Kūrorts Hurgadā un ekskursijas', 520.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Londona un Lielbritānija', 'Muzeji, teātri un pilsētas iepazīšana', 480.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Portugāles saule', 'Lisabona un Porto vīna dārzi', 590.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Japānas tradīcijas', 'Tokija un Kioto rudens noskaņās', 1500.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Turcijas all-inclusive', 'Antālijas piekrastes viesnīca', 430.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Islande - ledāji un geizeri', 'Ziemeļblāzmas medības un dabas brīnumi', 850.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Kipras saule', 'Atpūta Vidusjūras salā', 510.00)");
                stmt.executeUpdate("INSERT INTO CELOJUMS (NOSAUKUMS, APRAKSTS, CENA) VALUES ('Horvātijas pludmales', 'Dubrovnika un Adrijas jūras krasti', 620.00)");

                System.out.println("Visi 18 ceļojumi veiksmīgi pievienoti datubāzei!");
            } catch (SQLException e) {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

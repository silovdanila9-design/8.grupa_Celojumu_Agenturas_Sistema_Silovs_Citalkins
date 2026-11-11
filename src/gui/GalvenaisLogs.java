package gui;

import dao.CelojumsDAO;
import dao.LietotajsDAO;
import dao.MaksajumsDAO;
import dao.RezervacijaDAO;
import db.DatabaseManager;
import java.util.List;
import model.Celojums;
import model.Lietotajs;
import model.Maksajums;
import model.Rezervacija;
import util.Sesija;
import util.Validacija;
import javax.swing.JOptionPane;

public class GalvenaisLogs extends javax.swing.JFrame {

    private javax.swing.table.DefaultTableModel katModelis;

    private javax.swing.table.DefaultTableModel rezModelis;

    private javax.swing.table.DefaultTableModel makModelis;

    private javax.swing.table.DefaultTableModel vesModelis;

    private javax.swing.table.DefaultTableModel kliModelis;

    private int labojamaCelojumaId = -1;

    private javax.swing.JLabel fonaEtikete;

    private javax.swing.ImageIcon fonaOriginala;

    private static final java.awt.Color KRAASA_FONS =
            new java.awt.Color(16, 24, 44);

    private static final java.awt.Color KRAASA_FONS_GAISHS =
            new java.awt.Color(250, 241, 245);

    private static final java.awt.Color KRAASA_ROZA =
            new java.awt.Color(173, 20, 87);

    private static final java.awt.Color KRAASA_ROZA_GAISHS =
            new java.awt.Color(216, 27, 96);

    private static final java.awt.Color KRAASA_ROZA_TUMSA =
            new java.awt.Color(122, 12, 66);

    private static final java.awt.Color KRAASA_ROZA_GAI =
            new java.awt.Color(248, 187, 208);

    private static final java.awt.Font FONTS_PARASTS =
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);

    private static final java.awt.Font FONTS_VIRSR =
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20);

    private static final java.awt.Font FONTS_POGA =
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13);

    public GalvenaisLogs() {
        initComponents();
        dizains();
        tabuluModeli();
        aizpilditPalidzibu();
        atjaunotPoguStavokli();

        pack();
        setLocationRelativeTo(null);
    }

    private void dizains() {

        getContentPane().setBackground(KRAASA_FONS);
        uzliktFonaAttelu();
        lblTitulis.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
        lblTitulis.setForeground(java.awt.Color.WHITE);
        lblStatuss.setFont(FONTS_PARASTS);
        lblStatuss.setForeground(KRAASA_ROZA_GAI);

        stylePoga(btnPieteiksanas, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnRegistracija, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKatalogs, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnRezervacijas, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnMaksajumi, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnVesture, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKlienti, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnPalidziba, KRAASA_ROZA, java.awt.Color.WHITE);

        javax.swing.JDialog[] dialogi = {dlgPieteiksanas, dlgRegistracija,
            dlgKatalogs, dlgCelojums, dlgRezervacijas, dlgMaksajumi,
            dlgVesture, dlgKlienti, dlgPalidziba};
        javax.swing.JLabel[] virsraksti = {lblPieVirsraksts, lblRegVirsraksts,
            lblKatVirsraksts, lblCfVirsraksts, lblRezVirsraksts,
            lblMakVirsraksts, lblVesVirsraksts, lblKliVirsraksts,
            lblPalVirsraksts};
        for (javax.swing.JDialog d : dialogi) {
            d.getContentPane().setBackground(KRAASA_FONS_GAISHS);
        }

        menuJosla.setBackground(KRAASA_FONS);
        javax.swing.JMenu[] izvelnes = {menuDarbibas, menuPalidziba};
        for (javax.swing.JMenu m : izvelnes) {
            m.setOpaque(true);
            m.setBackground(KRAASA_FONS);
            m.setForeground(java.awt.Color.WHITE);
        }
        javax.swing.JMenuItem[] izvelnesVienumi = {miPieteiksanas,
            miRegistracija, miKatalogs, miRezervacijas, miMaksajumi,
            miVesture, miIzrakstities, miKlienti, miPalidziba,
            miParProgrammu};
        for (javax.swing.JMenuItem mi : izvelnesVienumi) {
            mi.setBackground(java.awt.Color.WHITE);
            mi.setForeground(new java.awt.Color(60, 48, 55));
        }

        for (javax.swing.JLabel l : virsraksti) {
            l.setFont(FONTS_VIRSR);
            l.setForeground(KRAASA_ROZA);
        }
        lblKatInfo.setFont(FONTS_PARASTS);
        lblKatInfo.setForeground(new java.awt.Color(118, 92, 104));
        lblMakInfo.setFont(FONTS_PARASTS);
        lblMakInfo.setForeground(new java.awt.Color(118, 92, 104));
        lblVesKopsavilkums.setFont(new java.awt.Font("Segoe UI",
                java.awt.Font.BOLD, 14));
        lblVesKopsavilkums.setForeground(KRAASA_ROZA_TUMSA);

        javax.swing.JLabel[] etiketes = {lblPieLietotajvards, lblPieParole,
            lblRegVards, lblRegUzvards, lblRegEpasts, lblRegTalrunis,
            lblRegLietotajvards, lblRegParole, lblRegParole2,
            lblKatMeklesana, lblRezKlients, lblRezCelojums, lblRezPersonas,
            lblMakRezervacija, lblMakSumma, lblMakVeids, lblVesStatuss,
            lblKliMeklesana, lblCfNosaukums, lblCfGalamerkis, lblCfApraksts,
            lblCfSakums, lblCfBeigas, lblCfCena, lblCfVietas};
        for (javax.swing.JLabel l : etiketes) {
            l.setFont(FONTS_PARASTS);
            l.setForeground(new java.awt.Color(70, 56, 64));
        }

        stylePoga(btnPieIeiet, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnPieAtcelt, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnRegSaglabat, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnRegAtcelt, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKatMeklet, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKatVisi, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKatPievienot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKatLabot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKatDzest, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKatAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnCfSaglabat, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnCfAtcelt, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnRezPievienot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnRezAtceltRez, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnRezDzest, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnRezAtjaunot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnRezAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnMakRekistr, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnMakDzest, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnMakAtjaunot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnMakAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnVesRadit, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnVesAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKliMeklet, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKliVisi, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKliDzest, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnKliAtjaunot, KRAASA_ROZA, java.awt.Color.WHITE);
        stylePoga(btnKliAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);
        stylePoga(btnPalAizvert, KRAASA_ROZA_TUMSA, java.awt.Color.WHITE);

        javax.swing.JTable[] tabulas = {tblKat, tblRez, tblMak, tblVes, tblKli};
        for (javax.swing.JTable t : tabulas) {
            t.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
            t.setRowHeight(24);
            t.setSelectionBackground(KRAASA_ROZA_GAI);
            t.setSelectionForeground(KRAASA_FONS);
            t.setGridColor(new java.awt.Color(236, 214, 224));
            t.getTableHeader().setBackground(KRAASA_ROZA);
            t.getTableHeader().setForeground(java.awt.Color.WHITE);
            t.getTableHeader().setFont(new java.awt.Font("Segoe UI",
                    java.awt.Font.BOLD, 13));
        }

        txtaPalidziba.setFont(new java.awt.Font("Segoe UI",
                java.awt.Font.PLAIN, 14));
        txtaPalidziba.setLineWrap(true);
        txtaPalidziba.setWrapStyleWord(true);
        txtaPalidziba.setEditable(false);
        txtaPalidziba.setBackground(java.awt.Color.WHITE);
    }

    private void stylePoga(javax.swing.JButton poga, java.awt.Color fons,
            java.awt.Color teksts) {
        poga.setBackground(fons);
        poga.setForeground(teksts);
        poga.setOpaque(true);
        poga.setBorderPainted(false);
        poga.setFocusPainted(false);
        poga.setFont(FONTS_POGA);
        poga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        poga.setMargin(new java.awt.Insets(8, 14, 8, 14));
        poga.putClientProperty("pogasKrasa", fons);

        poga.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                javax.swing.JButton p = (javax.swing.JButton) evt.getSource();
                if (p.isEnabled()) {
                    p.setBackground(KRAASA_ROZA_GAISHS);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                javax.swing.JButton p = (javax.swing.JButton) evt.getSource();
                java.awt.Color baze =
                        (java.awt.Color) p.getClientProperty("pogasKrasa");
                p.setBackground(baze != null ? baze : KRAASA_ROZA);
            }
        });
    }

    private void uzliktFonaAttelu() {
        java.net.URL url = getClass().getResource("/lidmasina.jpg");
        if (url == null) {
            System.out.println("Piezīme: fona attēls lidmasina.jpg nav atrasts "
                    + "— galvenais logs bez attēla.");
            return;
        }
        fonaOriginala = new javax.swing.ImageIcon(url);
        ((javax.swing.JPanel) getContentPane()).setOpaque(false);
        fonaEtikete = new javax.swing.JLabel();
        fonaEtikete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fonaEtikete.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        getLayeredPane().setLayer(fonaEtikete,
                javax.swing.JLayeredPane.FRAME_CONTENT_LAYER - 1);
        getLayeredPane().add(fonaEtikete);
        pielagotFonaAttelu();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                pielagotFonaAttelu();
            }
        });

        java.awt.EventQueue.invokeLater(this::pielagotFonaAttelu);
    }

    private void pielagotFonaAttelu() {
        if (fonaEtikete == null || fonaOriginala == null) {
            return;
        }

        int platums = Math.max(getLayeredPane().getWidth(), 1);
        int augstums = Math.max(getLayeredPane().getHeight(), 1);
        fonaEtikete.setBounds(0, 0, platums, augstums);
        double skala = Math.max(
                platums / (double) fonaOriginala.getIconWidth(),
                augstums / (double) fonaOriginala.getIconHeight());
        java.awt.Image mero = fonaOriginala.getImage().getScaledInstance(
                (int) (fonaOriginala.getIconWidth() * skala),
                (int) (fonaOriginala.getIconHeight() * skala),
                java.awt.Image.SCALE_AREA_AVERAGING);
        fonaEtikete.setIcon(new javax.swing.ImageIcon(mero));
    }

    private void tabuluModeli() {
        katModelis = jaunsModelis(new String[]{"ID", "Nosaukums",
            "Galamērķis", "Apraksts", "Sākuma datums", "Beigu datums",
            "Cena (EUR)", "Vietas"});
        tblKat.setModel(katModelis);
        rezModelis = jaunsModelis(new String[]{"ID", "Klients", "Ceļojums",
            "Personas", "Kopsumma (EUR)", "Statuss", "Izveidota"});
        tblRez.setModel(rezModelis);
        makModelis = jaunsModelis(new String[]{"ID", "Rezervācijas ID",
            "Klients", "Summa (EUR)", "Veids", "Datums"});
        tblMak.setModel(makModelis);
        vesModelis = jaunsModelis(new String[]{"ID", "Ceļojums", "Personas",
            "Kopsumma (EUR)", "Statuss", "Izveidota", "Apmaksāts (EUR)"});
        tblVes.setModel(vesModelis);
        kliModelis = jaunsModelis(new String[]{"ID", "Vārds", "Uzvārds",
            "E-pasts", "Tālrunis", "Lietotājvārds", "Loma"});
        tblKli.setModel(kliModelis);
    }

    private javax.swing.table.DefaultTableModel jaunsModelis(String[] kolonnas) {
        return new javax.swing.table.DefaultTableModel(kolonnas, 0) {
            @Override
            public boolean isCellEditable(int rinda, int kolonna) {
                return false;
            }
        };
    }

    private void aizpilditPalidzibu() {
        txtaPalidziba.setText(
            "KĀ LIETOT PROGRAMMU\n\n"
            + "1. PIETEIKŠANĀS — noklikšķiniet uz pogas \"Pieteikšanās sistēmā\" "
            + "un ievadiet lietotājvārdu un paroli. Administrators: admin / admin123.\n\n"
            + "2. REĢISTRĀCIJA — ja jums vēl nav konta, noklikšķiniet uz \"Jauna "
            + "klienta reģistrācija\" un aizpildiet visus laukus (e-pastam jāsatur @, "
            + "parolei jābūt vismaz 4 zīmes garai).\n\n"
            + "3. CEĻOJUMU KATALOGS — apskatiet pieejamos ceļojumus, izmantojiet "
            + "meklēšanu pēc nosaukuma, galamērķa vai apraksta. Administrators "
            + "papildus var pievienot, labot un dzēst ceļojumus.\n\n"
            + "4. REZERVĀCIJAS — izvēlieties ceļojumu, ievadiet personu skaitu "
            + "(1-10) un nospiediet \"Rezervēt\". Klients redz TIKAI savas "
            + "rezervācijas, administrators — visas. Rezervāciju var atcelt vai "
            + "izdzēst.\n\n"
            + "5. MAKSĀJUMU UZSKAITE — ievadiet rezervācijas ID (skat. Rezervācijas "
            + "logā), summu un veidu. Kad kopējā summa ir apmaksāta, rezervācijas "
            + "statuss automātiski mainās uz APMAKSATA.\n\n"
            + "6. REZERVĀCIJU VĒSTURE — visas jūsu (administrators: visu klientu) "
            + "rezervācijas ar statusa filtru un apmaksātās summas aprēķinu.\n\n"
            + "7. KLIENTU SARAKSTS — tikai administratoram: visi reģistrētie "
            + "lietotāji, meklēšana un dzēšana.\n\n"
            + "Programmas aizvēršana: noklikšķiniet uz loga krustziņas un "
            + "apstipriniet iziešanu. Dati tiek glabāti Apache Derby datu bāzē "
            + "un saglabājas arī pēc programmas aizvēršanas.");
    }

    private void atjaunotPoguStavokli() {
        boolean piesledzies = Sesija.irPiesledzies();
        boolean admin = Sesija.irAdministrators();

        btnPieteiksanas.setEnabled(!piesledzies);
        btnRegistracija.setEnabled(!piesledzies);
        btnKatalogs.setEnabled(piesledzies);
        btnRezervacijas.setEnabled(piesledzies);
        btnMaksajumi.setEnabled(piesledzies);
        btnVesture.setEnabled(piesledzies);
        btnKlienti.setEnabled(admin);
        btnPalidziba.setEnabled(true);

        miPieteiksanas.setEnabled(!piesledzies);
        miRegistracija.setEnabled(!piesledzies);
        miKatalogs.setEnabled(piesledzies);
        miRezervacijas.setEnabled(piesledzies);
        miMaksajumi.setEnabled(piesledzies);
        miVesture.setEnabled(piesledzies);
        miIzrakstities.setEnabled(piesledzies);
        miKlienti.setEnabled(admin);
        miPalidziba.setEnabled(true);

        if (piesledzies) {
            lblStatuss.setText("Pieslēdzies: " + Sesija.getPilnaisVards()
                    + "  (" + (admin ? "administrators" : "klients") + ")");
        } else {
            lblStatuss.setText("Nav pieslēgties — pieslēdzieties vai reģistrējieties");
        }
    }

    private void atvertDialogu(javax.swing.JDialog dialogs, int platums,
            int augstums, boolean uzGalveno) {
        dialogs.setModal(true);
        dialogs.setSize(platums, augstums);
        dialogs.setLocationRelativeTo(null);
        this.setVisible(false);
        dialogs.setVisible(true);
        dialogs.setVisible(false);
        dialogs.dispose();
        if (uzGalveno) {
            this.setVisible(true);
        }
    }

    private void zinojums(String teksts, String virsraksts, int tips) {
        JOptionPane.showMessageDialog(null, teksts, virsraksts, tips);
    }

    private String eur(double summa) {
        return String.format("%.2f", summa);
    }

    private Integer izveletaisId(javax.swing.JTable tabula) {
        int rinda = tabula.getSelectedRow();
        if (rinda < 0) {
            zinojums("Lūdzu, vispirms izvēlieties ierakstu no tabulas!",
                    "Brīdinājums", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (Integer) tabula.getValueAt(rinda, 0);
    }

    private int idNoKombo(javax.swing.JComboBox<String> cmb) {
        Object vienums = cmb.getSelectedItem();
        if (vienums == null) {
            return -1;
        }
        try {
            String s = vienums.toString();
            return Integer.parseInt(s.substring(0, s.indexOf(" — ")).trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private Integer parseSkaitli(String teksts) {
        try {
            return Integer.parseInt(teksts.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private void pieteikties() {
        String lietotajvards = txtPieLietotajvards.getText().trim();
        String parole = new String(pwdPieParole.getPassword());

        if (!Validacija.navTukss(lietotajvards) || !Validacija.navTukss(parole)) {
            zinojums("Lūdzu, ievadiet gan lietotājvārdu, gan paroli!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lietotajs lietotajs = new LietotajsDAO()
                .pieslegties(lietotajvards, parole);
        if (lietotajs == null) {
            pwdPieParole.setText("");
            zinojums("Nepareizs lietotājvārds vai parole!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sesija.setAktivaisLietotajs(lietotajs);
        atjaunotPoguStavokli();
        zinojums("Ieeja ir veiksmīga!\nSveiki, " + lietotajs.getVards() + "!",
                "Pieteikšanās", JOptionPane.INFORMATION_MESSAGE);
        dlgPieteiksanas.dispose();
    }

    private void registretKlientu() {
        String vards = txtRegVards.getText().trim();
        String uzvards = txtRegUzvards.getText().trim();
        String epasts = txtRegEpasts.getText().trim();
        String talrunis = txtRegTalrunis.getText().trim();
        String lietotajvards = txtRegLietotajvards.getText().trim();
        String parole1 = new String(pwdRegParole.getPassword());
        String parole2 = new String(pwdRegParole2.getPassword());

        if (!Validacija.navTukss(vards) || !Validacija.navTukss(uzvards)
                || !Validacija.navTukss(lietotajvards)) {
            zinojums("Lūdzu, aizpildiet vārdu, uzvārdu un lietotājvārdu!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validacija.pareizsEpasts(epasts)) {
            zinojums("Nederīga e-pasta adrese! Piemērs: janis@example.com",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validacija.pareizaParole(parole1)) {
            zinojums("Parolei jābūt vismaz 4 zīmes garai!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!parole1.equals(parole2)) {
            zinojums("Paroles nesakrīt!", "Validācija",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (new LietotajsDAO().lietotajvardsAiznemts(lietotajvards)) {
            zinojums("Lietotājvārds \"" + lietotajvards
                    + "\" jau eksistē! Izvēlieties citu.",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lietotajs jaunais = new Lietotajs(0, vards, uzvards, epasts,
                talrunis.isEmpty() ? null : talrunis, lietotajvards,
                parole1, "KLIENTS");
        int id = new LietotajsDAO().registret(jaunais);
        if (id > 0) {
            zinojums("Reģistrācija veiksmīga!\nJūsu konts Nr. " + id
                    + " izveidots. Tagad varat pieslēgties.",
                    "Reģistrācija", JOptionPane.INFORMATION_MESSAGE);
            notiritRegistracijasLaukus();
            dlgRegistracija.dispose();
        } else {
            zinojums("Neizdevās reģistrēt klientu datu bāzē!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notiritPieteiksanasLaukus() {
        txtPieLietotajvards.setText("");
        pwdPieParole.setText("");
    }

    private void notiritRegistracijasLaukus() {
        txtRegVards.setText("");
        txtRegUzvards.setText("");
        txtRegEpasts.setText("");
        txtRegTalrunis.setText("");
        txtRegLietotajvards.setText("");
        pwdRegParole.setText("");
        pwdRegParole2.setText("");
    }

    private void sagatavotKatalogu() {
        boolean admin = Sesija.irAdministrators();
        lblKatInfo.setText(admin
                ? "Administrators: var pievienot, labot un dzēst ceļojumus."
                : "Klients: apskatiet ceļojumus un meklējiet piemērotāko.");
        btnKatPievienot.setEnabled(admin);
        btnKatLabot.setEnabled(admin);
        btnKatDzest.setEnabled(admin);
        ieladetKatalogu(false);
    }

    private void ieladetKatalogu(boolean arMeklesanu) {
        try {
            List<Celojums> saraksts = arMeklesanu
                    ? new CelojumsDAO().mekli(txtKatMeklesana.getText())
                    : new CelojumsDAO().getVisi();
            katModelis.setRowCount(0);
            for (Celojums c : saraksts) {
                katModelis.addRow(new Object[]{
                    c.getId(),
                    c.getNosaukums(),
                    c.getGalamerkis(),
                    c.getApraksts(),
                    Validacija.datumaTeksts(c.getSakumaDatums()),
                    Validacija.datumaTeksts(c.getBeiguDatums()),
                    eur(c.getCena()),
                    c.getVietuSkaits()});
            }
            zinojums("Ceļojumu katalogs nolasīts no datu bāzes.\nAtrasti "
                    + saraksts.size() + " ceļojumi.",
                    "Katalogs", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            zinojums("Kļūda nolasot ceļojumus no datu bāzes:\n" + e.getMessage(),
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atvertCelojumaFormu(int celojumaId) {
        labojamaCelojumaId = celojumaId;
        if (celojumaId <= 0) {
            lblCfVirsraksts.setText("Jauna ceļojuma pievienošana");
            notiritCelojumaFormasLaukus();
        } else {
            Celojums c = new CelojumsDAO().pecId(celojumaId);
            if (c == null) {
                zinojums("Ceļojums Nr. " + celojumaId + " nav atrasts!",
                        "Kļūda", JOptionPane.ERROR_MESSAGE);
                return;
            }
            lblCfVirsraksts.setText("Ceļojuma labošana (ID " + celojumaId + ")");
            txtCfNosaukums.setText(c.getNosaukums());
            txtCfGalamerkis.setText(c.getGalamerkis());
            txtCfApraksts.setText(c.getApraksts() == null ? "" : c.getApraksts());
            txtCfSakums.setText(Validacija.datumaTeksts(c.getSakumaDatums()));
            txtCfBeigas.setText(Validacija.datumaTeksts(c.getBeiguDatums()));
            txtCfCena.setText(String.valueOf(c.getCena()));
            txtCfVietas.setText(String.valueOf(c.getVietuSkaits()));
        }
        dlgKatalogs.setVisible(false);
        atvertDialogu(dlgCelojums, 480, 420, false);
        dlgKatalogs.setVisible(true);
        ieladetKatalogu(false);
    }

    private void saglabatCelojumu() {
        String nosaukums = txtCfNosaukums.getText().trim();
        String galamerkis = txtCfGalamerkis.getText().trim();
        String apraksts = txtCfApraksts.getText().trim();

        if (!Validacija.navTukss(nosaukums) || !Validacija.navTukss(galamerkis)) {
            zinojums("Nosaukums un galamērķis nedrīkst būt tukši!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.time.LocalDate sakums = Validacija.parseDatums(txtCfSakums.getText());
        java.time.LocalDate beigas = Validacija.parseDatums(txtCfBeigas.getText());
        if (sakums == null || beigas == null) {
            zinojums("Datumi jāievada formātā dd.mm.gggg (piem., 15.08.2026)!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (beigas.isBefore(sakums)) {
            zinojums("Beigu datums nevar būt pirms sākuma datuma!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Double cena = Validacija.parseCena(txtCfCena.getText());
        if (cena == null) {
            zinojums("Cenai jābūt pozitīvam skaitlim (piem., 450.50)!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Integer vietas = Validacija.parsePersonas(txtCfVietas.getText());
        if (vietas == null) {
            zinojums("Vietu skaitam jābūt veselam skaitlim no 1 līdz 10!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Celojums c = new Celojums(labojamaCelojumaId, nosaukums, galamerkis,
                apraksts, sakums, beigas, cena, vietas);
        boolean veiksme = labojamaCelojumaId <= 0
                ? new CelojumsDAO().pievienot(c) > 0
                : new CelojumsDAO().labot(c);
        if (veiksme) {
            zinojums(labojamaCelojumaId <= 0
                    ? "Ceļojums \"" + nosaukums + "\" veiksmīgi pievienots katalogam!"
                    : "Ceļojuma izmaiņas veiksmīgi saglabātas!",
                    "Katalogs", JOptionPane.INFORMATION_MESSAGE);
            dlgCelojums.dispose();
        } else {
            zinojums("Neizdevās saglabāt ceļojumu datu bāzē!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dzestIzveletoCelojumu() {
        Integer id = izveletaisId(tblKat);
        if (id == null) {
            return;
        }
        int atbilde = JOptionPane.showConfirmDialog(null,
                "Vai tiešām vēlaties izdzēst ceļojumu Nr. " + id + "?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (atbilde != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (new CelojumsDAO().dzest(id)) {
                zinojums("Ceļojums Nr. " + id + " veiksmīgi izdzēsts!",
                        "Katalogs", JOptionPane.INFORMATION_MESSAGE);
                ieladetKatalogu(false);
            } else {
                zinojums("Ceļojums Nr. " + id + " nav atrasts!",
                        "Kļūda", JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.sql.SQLException e) {
            zinojums("Ceļojumu nevar izdzēst, jo uz to ir saistītas rezervācijas!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notiritCelojumaFormasLaukus() {
        txtCfNosaukums.setText("");
        txtCfGalamerkis.setText("");
        txtCfApraksts.setText("");
        txtCfSakums.setText("");
        txtCfBeigas.setText("");
        txtCfCena.setText("");
        txtCfVietas.setText("");
    }

    private void notiritKatMeklesanu() {
        txtKatMeklesana.setText("");
    }

    private void sagatavotRezervacijas() {
        boolean admin = Sesija.irAdministrators();
        pilditKlientuKombo();
        pilditCelojumuKombo();
        cmbRezKlients.setEnabled(admin);
        if (!admin && cmbRezKlients.getItemCount() > 0) {
            cmbRezKlients.setSelectedIndex(0);
        }
        ieladetRezervacijas();
    }

    private void pilditKlientuKombo() {
        cmbRezKlients.removeAllItems();
        if (Sesija.irAdministrators()) {
            for (Lietotajs l : new LietotajsDAO().getVisi()) {
                if ("KLIENTS".equals(l.getLoma())) {
                    cmbRezKlients.addItem(l.getId() + " — " + l.getVards()
                            + " " + l.getUzvards());
                }
            }
        } else {
            cmbRezKlients.addItem(Sesija.getLietotajaId() + " — "
                    + Sesija.getPilnaisVards());
        }
    }

    private void pilditCelojumuKombo() {
        cmbRezCelojums.removeAllItems();
        for (Celojums c : new CelojumsDAO().getVisi()) {
            cmbRezCelojums.addItem(c.getId() + " — " + c.toString());
        }
    }

    private void ieladetRezervacijas() {
        try {
            int lietId = Sesija.irAdministrators() ? -1 : Sesija.getLietotajaId();
            List<Object[]> rindas = new RezervacijaDAO().getTabula(lietId, null);
            rezModelis.setRowCount(0);
            for (Object[] r : rindas) {
                r[4] = eur((Double) r[4]);
                rezModelis.addRow(r);
            }
            zinojums("Rezervācijas nolasītas no datu bāzes.\nAtrastas "
                    + rindas.size() + " rezervācijas.",
                    "Rezervācijas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            zinojums("Kļūda nolasot rezervācijas:\n" + e.getMessage(),
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void izveidotRezervaciju() {
        int celojumaId = idNoKombo(cmbRezCelojums);
        if (celojumaId <= 0) {
            zinojums("Lūdzu, izvēlieties ceļojumu no saraksta!",
                    "Brīdinājums", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer personas = Validacija.parsePersonas(txtRezPersonas.getText());
        if (personas == null) {
            zinojums("Personu skaitam jābūt veselam skaitlim no 1 līdz 10!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int lietajaId = Sesija.irAdministrators()
                ? idNoKombo(cmbRezKlients) : Sesija.getLietotajaId();
        if (lietajaId <= 0) {
            zinojums("Lūdzu, izvēlieties klientu!",
                    "Brīdinājums", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Celojums c = new CelojumsDAO().pecId(celojumaId);
        if (c == null) {
            zinojums("Ceļojums nav atrasts datu bāzē!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (personas > c.getVietuSkaits()) {
            zinojums("Pārsniegts pieejamo vietu skaits (" + c.getVietuSkaits()
                    + ")!", "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double kopsumma = Math.round(c.getCena() * personas * 100.0) / 100.0;
        Rezervacija r = new Rezervacija(0, lietajaId, celojumaId, personas,
                kopsumma, Rezervacija.STATUS_AKTIVA, null);
        int id = new RezervacijaDAO().pievienot(r);
        if (id > 0) {
            zinojums("Rezervācija Nr. " + id + " veiksmīgi izveidota!\n"
                    + "Ceļojums: " + c.getNosaukums() + "\n"
                    + "Personas: " + personas + "\n"
                    + "Kopsumma: " + eur(kopsumma) + " EUR\n\n"
                    + "Apmaksai izmantojiet logu \"Maksājumu uzskaite\".",
                    "Rezervācijas", JOptionPane.INFORMATION_MESSAGE);
            txtRezPersonas.setText("");
            ieladetRezervacijas();
        } else {
            zinojums("Neizdevās izveidot rezervāciju!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean rezervacijaAtļauta(int id) {
        if (!Sesija.irAdministrators()
                && !new RezervacijaDAO().piederLietotajam(id,
                        Sesija.getLietotajaId())) {
            zinojums("Varat pārvaldīt tikai SAVAS rezervācijas!",
                    "Piekļuve liegta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void atceltRezervaciju() {
        Integer id = izveletaisId(tblRez);
        if (id == null || !rezervacijaAtļauta(id)) {
            return;
        }
        int atbilde = JOptionPane.showConfirmDialog(null,
                "Vai tiešām vēlaties atcelt rezervāciju Nr. " + id + "?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (atbilde != JOptionPane.YES_OPTION) {
            return;
        }
        if (new RezervacijaDAO().mainitStatusu(id, Rezervacija.STATUS_ATCELTA)) {
            zinojums("Rezervācija Nr. " + id + " atcelta.",
                    "Rezervācijas", JOptionPane.INFORMATION_MESSAGE);
            ieladetRezervacijas();
        } else {
            zinojums("Neizdevās mainīt rezervācijas statusu!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dzestRezervaciju() {
        Integer id = izveletaisId(tblRez);
        if (id == null || !rezervacijaAtļauta(id)) {
            return;
        }
        int atbilde = JOptionPane.showConfirmDialog(null,
                "Vai tiešām vēlaties IZDZĒST rezervāciju Nr. " + id
                + " (līdz ar to arī tās maksājumus)?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (atbilde != JOptionPane.YES_OPTION) {
            return;
        }
        if (new RezervacijaDAO().dzest(id)) {
            zinojums("Rezervācija Nr. " + id + " izdzēsta.",
                    "Rezervācijas", JOptionPane.INFORMATION_MESSAGE);
            ieladetRezervacijas();
        } else {
            zinojums("Rezervācija Nr. " + id + " nav atrasta!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notiritRezIevadi() {
        txtRezPersonas.setText("");
    }

    private void sagatavotMaksajumus() {
        boolean admin = Sesija.irAdministrators();
        lblMakInfo.setText(admin
                ? "Administrators: var reģistrēt maksājumus jebkurai rezervācijai un dzēst maksājumus."
                : "Klients: var apmaksāt tikai SAVAS rezervācijas.");
        btnMakDzest.setEnabled(admin);
        if (cmbMakVeids.getItemCount() == 0) {
            cmbMakVeids.addItem("KARTE");
            cmbMakVeids.addItem("PARSKAITIJUMS");
            cmbMakVeids.addItem("SKAIDRA_NAUDA");
        }
        ieladetMaksajumus();
    }

    private void ieladetMaksajumus() {
        try {
            int lietId = Sesija.irAdministrators() ? -1 : Sesija.getLietotajaId();
            List<Object[]> rindas = new MaksajumsDAO().getTabula(lietId);
            makModelis.setRowCount(0);
            for (Object[] r : rindas) {
                r[3] = eur((Double) r[3]);
                makModelis.addRow(r);
            }
            zinojums("Maksājumi nolasīti no datu bāzes.\nAtrasti "
                    + rindas.size() + " maksājumi.",
                    "Maksājumi", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            zinojums("Kļūda nolasot maksājumus:\n" + e.getMessage(),
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registretMaksajumu() {
        Integer rezId = parseSkaitli(txtMakRezervacija.getText());
        if (rezId == null) {
            zinojums("Ievadiet rezervācijas ID (skaitli)!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Rezervacija r = new RezervacijaDAO().pecId(rezId);
        if (r == null) {
            zinojums("Rezervācija Nr. " + rezId + " nav atrasta!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Sesija.irAdministrators()
                && !new RezervacijaDAO().piederLietotajam(rezId,
                        Sesija.getLietotajaId())) {
            zinojums("Varat apmaksāt tikai SAVAS rezervācijas!",
                    "Piekļuve liegta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Rezervacija.STATUS_ATCELTA.equals(r.getStatuss())) {
            zinojums("Atceltā rezervācija nav apmaksājama!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Double summa = Validacija.parseCena(txtMakSumma.getText());
        if (summa == null) {
            zinojums("Ievadiet derīgu pozitīvu summu (piem., 100.50)!",
                    "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double apmaksats = new RezervacijaDAO().apmaksataSumma(rezId);
        double atlikums = Math.round((r.getKopsumma() - apmaksats) * 100.0) / 100.0;
        if (atlikums <= 0) {
            zinojums("Rezervācija Nr. " + rezId + " jau ir pilnībā apmaksāta!",
                    "Validācija", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (summa > atlikums + 0.001) {
            zinojums("Summa pārsniedz apmaksāmo atlikumu (" + eur(atlikums)
                    + " EUR)!", "Validācija", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String veids = (String) cmbMakVeids.getSelectedItem();
        Maksajums m = new Maksajums(0, rezId, summa, null, veids);
        int id = new MaksajumsDAO().registretArStatusaNovertejumu(m, r);
        if (id > 0) {
            String info = "Maksājums Nr. " + id + " reģistrēts: "
                    + eur(summa) + " EUR (" + veids + ").\n";
            if (Rezervacija.STATUS_APMAKSATA.equals(
                    new RezervacijaDAO().pecId(rezId).getStatuss())) {
                info += "Rezervācija Nr. " + rezId
                        + " ir pilnībā apmaksāta!\nStatuss mainīts uz APMAKSATA.";
            } else {
                double jaunsApmaksats = Math.round((apmaksats + summa) * 100.0) / 100.0;
                info += "Vēl jāmaksā: " + eur(r.getKopsumma() - jaunsApmaksats)
                        + " EUR.";
            }
            zinojums(info, "Maksājumi", JOptionPane.INFORMATION_MESSAGE);
            txtMakSumma.setText("");
            ieladetMaksajumus();
        } else {
            zinojums("Neizdevās reģistrēt maksājumu!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dzestMaksajumu() {
        if (!Sesija.irAdministrators()) {
            zinojums("Maksājumus var dzēst tikai administrators!",
                    "Piekļuve liegta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Integer id = izveletaisId(tblMak);
        if (id == null) {
            return;
        }
        Maksajums m = new MaksajumsDAO().pecId(id);
        if (m == null) {
            zinojums("Maksājums Nr. " + id + " nav atrasts!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int atbilde = JOptionPane.showConfirmDialog(null,
                "Vai tiešām vēlaties izdzēst maksājumu Nr. " + id + "?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (atbilde != JOptionPane.YES_OPTION) {
            return;
        }
        if (new MaksajumsDAO().dzest(id)) {
            Rezervacija r = new RezervacijaDAO().pecId(m.getRezervacijaId());
            if (r != null && Rezervacija.STATUS_APMAKSATA.equals(r.getStatuss())
                    && new RezervacijaDAO().apmaksataSumma(r.getId())
                            < r.getKopsumma() - 0.001) {
                new RezervacijaDAO().mainitStatusu(r.getId(),
                        Rezervacija.STATUS_AKTIVA);
            }
            zinojums("Maksājums Nr. " + id + " izdzēsts.",
                    "Maksājumi", JOptionPane.INFORMATION_MESSAGE);
            ieladetMaksajumus();
        } else {
            zinojums("Neizdevās dzēst maksājumu!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notiritMakIevadi() {
        txtMakRezervacija.setText("");
        txtMakSumma.setText("");
    }

    private void sagatavotVesture() {
        if (cmbVesStatuss.getItemCount() == 0) {
            cmbVesStatuss.addItem("VISAS");
            for (String s : Validacija.statusuSaraksts()) {
                cmbVesStatuss.addItem(s);
            }
        }
        ieladetVesture();
    }

    private void ieladetVesture() {
        try {
            int lietId = Sesija.irAdministrators() ? -1 : Sesija.getLietotajaId();
            String statuss = (String) cmbVesStatuss.getSelectedItem();
            List<Object[]> rindas = new RezervacijaDAO().getVesture(lietId, statuss);
            vesModelis.setRowCount(0);
            double kopsumma = 0;
            double apmaksats = 0;
            for (Object[] r : rindas) {
                kopsumma += (Double) r[3];
                apmaksats += (Double) r[6];
                r[3] = eur((Double) r[3]);
                r[6] = eur((Double) r[6]);
                vesModelis.addRow(r);
            }
            lblVesKopsavilkums.setText("Kopā rezervāciju: " + rindas.size()
                    + "   |   Kopsumma: " + eur(kopsumma)
                    + " EUR   |   Apmaksāts: " + eur(apmaksats) + " EUR");
            zinojums("Vēsture nolasīta no datu bāzes.\nAtrastas "
                    + rindas.size() + " rezervācijas.",
                    "Vēsture", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            zinojums("Kļūda nolasot vēsturi:\n" + e.getMessage(),
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sagatavotKlientus() {
        ieladetKlientus(false);
    }

    private void ieladetKlientus(boolean arMeklesanu) {
        try {
            List<Lietotajs> saraksts = arMeklesanu
                    ? new LietotajsDAO().mekleli(txtKliMeklesana.getText())
                    : new LietotajsDAO().getVisi();
            kliModelis.setRowCount(0);
            for (Lietotajs l : saraksts) {
                kliModelis.addRow(new Object[]{
                    l.getId(), l.getVards(), l.getUzvards(), l.getEpasts(),
                    l.getTalrunis() == null ? "" : l.getTalrunis(),
                    l.getLietotajvards(), l.getLoma()});
            }
            zinojums("Klientu saraksts nolasīts no datu bāzes.\nAtrasti "
                    + saraksts.size() + " lietotāji.",
                    "Klienti", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            zinojums("Kļūda nolasot lietotājus:\n" + e.getMessage(),
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dzestKlientu() {
        Integer id = izveletaisId(tblKli);
        if (id == null) {
            return;
        }
        if (id == Sesija.getLietotajaId()) {
            zinojums("Nevar izdzēst paša savu kontu!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int atbilde = JOptionPane.showConfirmDialog(null,
                "Līdz ar klientu tiks izdzēstas arī visas viņa rezervācijas "
                + "un maksājumi!\nTurpināt?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (atbilde != JOptionPane.YES_OPTION) {
            return;
        }
        if (new LietotajsDAO().dzest(id)) {
            zinojums("Klients Nr. " + id + " izdzēsts.",
                    "Klienti", JOptionPane.INFORMATION_MESSAGE);
            ieladetKlientus(false);
        } else {
            zinojums("Neizdevās dzēst klientu!",
                    "Kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void notiritKliMeklesanu() {
        txtKliMeklesana.setText("");
    }

    private void atvertPieteiksanos() {
        atvertDialogu(dlgPieteiksanas, 420, 230, true);
        notiritPieteiksanasLaukus();
    }

    private void atvertRegistraciju() {
        atvertDialogu(dlgRegistracija, 460, 420, true);
        notiritRegistracijasLaukus();
    }

    private void atvertKatalogu() {
        sagatavotKatalogu();
        atvertDialogu(dlgKatalogs, 940, 600, true);
        notiritKatMeklesanu();
    }

    private void atvertRezervacijas() {
        sagatavotRezervacijas();
        atvertDialogu(dlgRezervacijas, 960, 640, true);
        notiritRezIevadi();
    }

    private void atvertMaksajumus() {
        sagatavotMaksajumus();
        atvertDialogu(dlgMaksajumi, 940, 620, true);
        notiritMakIevadi();
    }

    private void atvertVesture() {
        sagatavotVesture();
        atvertDialogu(dlgVesture, 940, 620, true);
    }

    private void atvertKlientus() {
        if (!Sesija.irAdministrators()) {
            zinojums("Klientu saraksts pieejams tikai administratoram!",
                    "Piekļuve liegta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        sagatavotKlientus();
        atvertDialogu(dlgKlienti, 940, 560, true);
        notiritKliMeklesanu();
    }

    private void atvertPalidzibu() {
        atvertDialogu(dlgPalidziba, 700, 560, true);
    }

    private void izrakstities() {
        Sesija.izrakstities();
        atjaunotPoguStavokli();
        zinojums("Jūs esat izrakstījies no sistēmas.",
                "Izrakstīšanās", JOptionPane.INFORMATION_MESSAGE);
    }

    private void parProgrammu() {
        zinojums("Ceļojumu aģentūras sistēma v1.0\n\n"
                + "Autori: Danils Silovs un Ričards Čitalkins (8. grupa)\n"
                + "Tehnoloģijas: Java 17, NetBeans GUI Builder, "
                + "Apache Derby (JDBC), JUnit4, Git/GitHub",
                "Par programmu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void izietNoProgrammas() {
        int atbilde = JOptionPane.showConfirmDialog(this,
                "Vai tiešām vēlaties iziet no programmas?",
                "Apstiprinājums", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (atbilde == JOptionPane.YES_OPTION) {
            Sesija.izrakstities();
            DatabaseManager.aizvertDatubazi();
            System.exit(0);
        }
    }

    public static void main(String args[]) {

        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GalvenaisLogs.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GalvenaisLogs().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuJosla = new javax.swing.JMenuBar();
        menuDarbibas = new javax.swing.JMenu();
        miPieteiksanas = new javax.swing.JMenuItem();
        miRegistracija = new javax.swing.JMenuItem();
        miKatalogs = new javax.swing.JMenuItem();
        miRezervacijas = new javax.swing.JMenuItem();
        miMaksajumi = new javax.swing.JMenuItem();
        miVesture = new javax.swing.JMenuItem();
        miIzrakstities = new javax.swing.JMenuItem();
        miKlienti = new javax.swing.JMenuItem();
        menuPalidziba = new javax.swing.JMenu();
        miPalidziba = new javax.swing.JMenuItem();
        miParProgrammu = new javax.swing.JMenuItem();

        menuDarbibas.setText("Darbības");
        miPieteiksanas.setText("Pieteikšanās sistēmā");
        miPieteiksanas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPieteiksanasActionPerformed(evt);
            }
        });
        menuDarbibas.add(miPieteiksanas);
        miRegistracija.setText("Jauna klienta reģistrācija");
        miRegistracija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRegistracijaActionPerformed(evt);
            }
        });
        menuDarbibas.add(miRegistracija);
        miKatalogs.setText("Ceļojumu katalogs");
        miKatalogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miKatalogsActionPerformed(evt);
            }
        });
        menuDarbibas.add(miKatalogs);
        miRezervacijas.setText("Rezervācijas");
        miRezervacijas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRezervacijasActionPerformed(evt);
            }
        });
        menuDarbibas.add(miRezervacijas);
        miMaksajumi.setText("Maksājumu uzskaite");
        miMaksajumi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miMaksajumiActionPerformed(evt);
            }
        });
        menuDarbibas.add(miMaksajumi);
        miVesture.setText("Rezervāciju vēsture");
        miVesture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVestureActionPerformed(evt);
            }
        });
        menuDarbibas.add(miVesture);
        miIzrakstities.setText("Izrakstīties");
        miIzrakstities.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miIzrakstitiesActionPerformed(evt);
            }
        });
        menuDarbibas.add(miIzrakstities);
        miKlienti.setText("Klientu saraksts (admins)");
        miKlienti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miKlientiActionPerformed(evt);
            }
        });
        menuDarbibas.add(miKlienti);
        menuJosla.add(menuDarbibas);
        menuPalidziba.setText("Palīdzība");
        miPalidziba.setText("Kā lietot programmu");
        miPalidziba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPalidzibaActionPerformed(evt);
            }
        });
        menuPalidziba.add(miPalidziba);
        miParProgrammu.setText("Par programmu");
        miParProgrammu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miParProgrammuActionPerformed(evt);
            }
        });
        menuPalidziba.add(miParProgrammu);
        menuJosla.add(menuPalidziba);
        setJMenuBar(menuJosla);

        dlgPieteiksanas = new javax.swing.JDialog();

        lblPieVirsraksts = new javax.swing.JLabel();
        lblPieLietotajvards = new javax.swing.JLabel();
        txtPieLietotajvards = new javax.swing.JTextField();
        lblPieParole = new javax.swing.JLabel();
        pwdPieParole = new javax.swing.JPasswordField();
        btnPieIeiet = new javax.swing.JButton();
        btnPieAtcelt = new javax.swing.JButton();

        lblPieVirsraksts.setText("Pieteikšanās sistēmā");
        lblPieLietotajvards.setText("Lietotājvārds:");
        lblPieParole.setText("Parole:");
        btnPieIeiet.setText("Ielogoties");
        btnPieIeiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPieIeietActionPerformed(evt);
            }
        });
        btnPieAtcelt.setText("Atcelt");
        btnPieAtcelt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPieAtceltActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgPieteiksanasLayout = new javax.swing.GroupLayout(dlgPieteiksanas.getContentPane());
        dlgPieteiksanas.getContentPane().setLayout(dlgPieteiksanasLayout);
        dlgPieteiksanasLayout.setHorizontalGroup(
            dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgPieteiksanasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblPieVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgPieteiksanasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblPieLietotajvards)
              .addComponent(lblPieParole))
            .addGap(12, 12, 12)
            .addGroup(dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(txtPieLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdPieParole, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20, 20, 20))
          .addGroup(dlgPieteiksanasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnPieIeiet)
            .addGap(10, 10, 10)
            .addComponent(btnPieAtcelt)
            .addGap(20, 20, 20))
        );
        dlgPieteiksanasLayout.setVerticalGroup(
            dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgPieteiksanasLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblPieVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblPieLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtPieLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblPieParole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdPieParole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgPieteiksanasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnPieIeiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnPieAtcelt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgPieteiksanas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgPieteiksanas.setTitle("Pieteikšanās sistēmā");

        dlgRegistracija = new javax.swing.JDialog();

        lblRegVirsraksts = new javax.swing.JLabel();
        lblRegVards = new javax.swing.JLabel();
        txtRegVards = new javax.swing.JTextField();
        lblRegUzvards = new javax.swing.JLabel();
        txtRegUzvards = new javax.swing.JTextField();
        lblRegEpasts = new javax.swing.JLabel();
        txtRegEpasts = new javax.swing.JTextField();
        lblRegTalrunis = new javax.swing.JLabel();
        txtRegTalrunis = new javax.swing.JTextField();
        lblRegLietotajvards = new javax.swing.JLabel();
        txtRegLietotajvards = new javax.swing.JTextField();
        lblRegParole = new javax.swing.JLabel();
        pwdRegParole = new javax.swing.JPasswordField();
        lblRegParole2 = new javax.swing.JLabel();
        pwdRegParole2 = new javax.swing.JPasswordField();
        btnRegSaglabat = new javax.swing.JButton();
        btnRegAtcelt = new javax.swing.JButton();

        lblRegVirsraksts.setText("Jauna klienta reģistrācija");
        lblRegVards.setText("Vārds:");
        lblRegUzvards.setText("Uzvārds:");
        lblRegEpasts.setText("E-pasts:");
        lblRegTalrunis.setText("Tālrunis:");
        lblRegLietotajvards.setText("Lietotājvārds:");
        lblRegParole.setText("Parole:");
        lblRegParole2.setText("Atkārtot paroli:");
        btnRegSaglabat.setText("Reģistrēties");
        btnRegSaglabat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegSaglabatActionPerformed(evt);
            }
        });
        btnRegAtcelt.setText("Atcelt");
        btnRegAtcelt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegAtceltActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgRegistracijaLayout = new javax.swing.GroupLayout(dlgRegistracija.getContentPane());
        dlgRegistracija.getContentPane().setLayout(dlgRegistracijaLayout);
        dlgRegistracijaLayout.setHorizontalGroup(
            dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgRegistracijaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblRegVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgRegistracijaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegVards)
              .addComponent(lblRegUzvards)
              .addComponent(lblRegEpasts)
              .addComponent(lblRegTalrunis)
              .addComponent(lblRegLietotajvards)
              .addComponent(lblRegParole)
              .addComponent(lblRegParole2))
            .addGap(12, 12, 12)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(txtRegVards, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegUzvards, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegEpasts, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegTalrunis, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdRegParole, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdRegParole2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20, 20, 20))
          .addGroup(dlgRegistracijaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnRegSaglabat)
            .addGap(10, 10, 10)
            .addComponent(btnRegAtcelt)
            .addGap(20, 20, 20))
        );
        dlgRegistracijaLayout.setVerticalGroup(
            dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgRegistracijaLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblRegVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegVards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegVards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegUzvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegUzvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegEpasts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegEpasts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegTalrunis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegTalrunis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRegLietotajvards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegParole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdRegParole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRegParole2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(pwdRegParole2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgRegistracijaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnRegSaglabat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRegAtcelt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgRegistracija.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgRegistracija.setTitle("Jauna klienta reģistrācija");

        dlgKatalogs = new javax.swing.JDialog();

        scpKat = new javax.swing.JScrollPane();
        lblKatVirsraksts = new javax.swing.JLabel();
        lblKatInfo = new javax.swing.JLabel();
        lblKatMeklesana = new javax.swing.JLabel();
        txtKatMeklesana = new javax.swing.JTextField();
        btnKatMeklet = new javax.swing.JButton();
        btnKatVisi = new javax.swing.JButton();
        tblKat = new javax.swing.JTable();
        btnKatPievienot = new javax.swing.JButton();
        btnKatLabot = new javax.swing.JButton();
        btnKatDzest = new javax.swing.JButton();
        btnKatAizvert = new javax.swing.JButton();

        lblKatVirsraksts.setText("Ceļojumu katalogs");
        lblKatMeklesana.setText("Meklēt:");
        btnKatMeklet.setText("Meklēt");
        btnKatMeklet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatMekletActionPerformed(evt);
            }
        });
        btnKatVisi.setText("Rādīt visus");
        btnKatVisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatVisiActionPerformed(evt);
            }
        });
        tblKat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID",
                "Nosaukums",
                "Galamērķis",
                "Apraksts",
                "Sākuma datums",
                "Beigu datums",
                "Cena (EUR)",
                "Vietas"
            }
        ));
        btnKatPievienot.setText("Pievienot ceļojumu");
        btnKatPievienot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatPievienotActionPerformed(evt);
            }
        });
        btnKatLabot.setText("Labot ceļojumu");
        btnKatLabot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatLabotActionPerformed(evt);
            }
        });
        btnKatDzest.setText("Dzēst ceļojumu");
        btnKatDzest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatDzestActionPerformed(evt);
            }
        });
        btnKatAizvert.setText("Aizvērt");
        btnKatAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatAizvertActionPerformed(evt);
            }
        });
        scpKat.setViewportView(tblKat);

        javax.swing.GroupLayout dlgKatalogsLayout = new javax.swing.GroupLayout(dlgKatalogs.getContentPane());
        dlgKatalogs.getContentPane().setLayout(dlgKatalogsLayout);
        dlgKatalogsLayout.setHorizontalGroup(
            dlgKatalogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblKatVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblKatInfo)
            .addGap(20, 20, 20))
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblKatMeklesana)
            .addGap(10, 10, 10)
            .addComponent(txtKatMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnKatMeklet)
            .addGap(10, 10, 10)
            .addComponent(btnKatVisi)
            .addGap(20, 20, 20))
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpKat, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKatPievienot)
            .addGap(10, 10, 10)
            .addComponent(btnKatLabot)
            .addGap(10, 10, 10)
            .addComponent(btnKatDzest)
            .addGap(20, 20, 20))
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKatAizvert)
            .addGap(20, 20, 20))
        );
        dlgKatalogsLayout.setVerticalGroup(
            dlgKatalogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgKatalogsLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblKatVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addComponent(lblKatInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(dlgKatalogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblKatMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtKatMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKatMeklet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKatVisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(scpKat, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addGroup(dlgKatalogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKatPievienot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKatLabot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKatDzest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgKatalogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKatAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgKatalogs.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgKatalogs.setTitle("Ceļojumu katalogs");

        dlgCelojums = new javax.swing.JDialog();

        lblCfVirsraksts = new javax.swing.JLabel();
        lblCfNosaukums = new javax.swing.JLabel();
        txtCfNosaukums = new javax.swing.JTextField();
        lblCfGalamerkis = new javax.swing.JLabel();
        txtCfGalamerkis = new javax.swing.JTextField();
        lblCfApraksts = new javax.swing.JLabel();
        txtCfApraksts = new javax.swing.JTextField();
        lblCfSakums = new javax.swing.JLabel();
        txtCfSakums = new javax.swing.JTextField();
        lblCfBeigas = new javax.swing.JLabel();
        txtCfBeigas = new javax.swing.JTextField();
        lblCfCena = new javax.swing.JLabel();
        txtCfCena = new javax.swing.JTextField();
        lblCfVietas = new javax.swing.JLabel();
        txtCfVietas = new javax.swing.JTextField();
        btnCfSaglabat = new javax.swing.JButton();
        btnCfAtcelt = new javax.swing.JButton();

        lblCfVirsraksts.setText("Ceļojuma dati");
        lblCfNosaukums.setText("Nosaukums:");
        lblCfGalamerkis.setText("Galamērķis:");
        lblCfApraksts.setText("Apraksts:");
        lblCfSakums.setText("Sākuma datums (dd.mm.gggg):");
        lblCfBeigas.setText("Beigu datums (dd.mm.gggg):");
        lblCfCena.setText("Cena (EUR):");
        lblCfVietas.setText("Vietu skaits:");
        btnCfSaglabat.setText("Saglabāt");
        btnCfSaglabat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCfSaglabatActionPerformed(evt);
            }
        });
        btnCfAtcelt.setText("Atcelt");
        btnCfAtcelt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCfAtceltActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgCelojumsLayout = new javax.swing.GroupLayout(dlgCelojums.getContentPane());
        dlgCelojums.getContentPane().setLayout(dlgCelojumsLayout);
        dlgCelojumsLayout.setHorizontalGroup(
            dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgCelojumsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblCfVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgCelojumsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfNosaukums)
              .addComponent(lblCfGalamerkis)
              .addComponent(lblCfApraksts)
              .addComponent(lblCfSakums)
              .addComponent(lblCfBeigas)
              .addComponent(lblCfCena)
              .addComponent(lblCfVietas))
            .addGap(12, 12, 12)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(txtCfNosaukums, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfGalamerkis, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfApraksts, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfSakums, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfBeigas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfCena, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfVietas, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20, 20, 20))
          .addGroup(dlgCelojumsLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnCfSaglabat)
            .addGap(10, 10, 10)
            .addComponent(btnCfAtcelt)
            .addGap(20, 20, 20))
        );
        dlgCelojumsLayout.setVerticalGroup(
            dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgCelojumsLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblCfVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfNosaukums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfNosaukums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfGalamerkis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfGalamerkis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfApraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfApraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfSakums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfSakums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfBeigas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfBeigas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfCena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(8, 8, 8)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblCfVietas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtCfVietas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgCelojumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnCfSaglabat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnCfAtcelt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgCelojums.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgCelojums.setTitle("Ceļojuma dati");

        dlgRezervacijas = new javax.swing.JDialog();

        scpRez = new javax.swing.JScrollPane();
        lblRezVirsraksts = new javax.swing.JLabel();
        lblRezKlients = new javax.swing.JLabel();
        cmbRezKlients = new javax.swing.JComboBox<String>();
        lblRezCelojums = new javax.swing.JLabel();
        cmbRezCelojums = new javax.swing.JComboBox<String>();
        lblRezPersonas = new javax.swing.JLabel();
        txtRezPersonas = new javax.swing.JTextField();
        btnRezPievienot = new javax.swing.JButton();
        tblRez = new javax.swing.JTable();
        btnRezAtceltRez = new javax.swing.JButton();
        btnRezDzest = new javax.swing.JButton();
        btnRezAtjaunot = new javax.swing.JButton();
        btnRezAizvert = new javax.swing.JButton();

        lblRezVirsraksts.setText("Rezervāciju pārvaldība");
        lblRezKlients.setText("Klients:");
        lblRezCelojums.setText("Ceļojums:");
        lblRezPersonas.setText("Personu skaits:");
        btnRezPievienot.setText("Rezervēt");
        btnRezPievienot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezPievienotActionPerformed(evt);
            }
        });
        tblRez.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID",
                "Klients",
                "Ceļojums",
                "Personas",
                "Kopsumma (EUR)",
                "Statuss",
                "Izveidota"
            }
        ));
        btnRezAtceltRez.setText("Atcelt rezervāciju");
        btnRezAtceltRez.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezAtceltRezActionPerformed(evt);
            }
        });
        btnRezDzest.setText("Dzēst rezervāciju");
        btnRezDzest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezDzestActionPerformed(evt);
            }
        });
        btnRezAtjaunot.setText("Atjaunot sarakstu");
        btnRezAtjaunot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezAtjaunotActionPerformed(evt);
            }
        });
        btnRezAizvert.setText("Aizvērt");
        btnRezAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezAizvertActionPerformed(evt);
            }
        });
        scpRez.setViewportView(tblRez);

        javax.swing.GroupLayout dlgRezervacijasLayout = new javax.swing.GroupLayout(dlgRezervacijas.getContentPane());
        dlgRezervacijas.getContentPane().setLayout(dlgRezervacijasLayout);
        dlgRezervacijasLayout.setHorizontalGroup(
            dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblRezVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblRezKlients)
            .addGap(10, 10, 10)
            .addComponent(cmbRezKlients, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblRezCelojums)
            .addGap(10, 10, 10)
            .addComponent(cmbRezCelojums, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblRezPersonas)
            .addGap(10, 10, 10)
            .addComponent(txtRezPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnRezPievienot)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpRez, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnRezAtceltRez)
            .addGap(10, 10, 10)
            .addComponent(btnRezDzest)
            .addGap(10, 10, 10)
            .addComponent(btnRezAtjaunot)
            .addGap(20, 20, 20))
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnRezAizvert)
            .addGap(20, 20, 20))
        );
        dlgRezervacijasLayout.setVerticalGroup(
            dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgRezervacijasLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblRezVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRezKlients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(cmbRezKlients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRezCelojums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(cmbRezCelojums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblRezPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtRezPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRezPievienot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(scpRez, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addGroup(dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnRezAtceltRez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRezDzest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRezAtjaunot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgRezervacijasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnRezAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgRezervacijas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgRezervacijas.setTitle("Rezervācijas");

        dlgMaksajumi = new javax.swing.JDialog();

        scpMak = new javax.swing.JScrollPane();
        lblMakVirsraksts = new javax.swing.JLabel();
        lblMakInfo = new javax.swing.JLabel();
        lblMakRezervacija = new javax.swing.JLabel();
        txtMakRezervacija = new javax.swing.JTextField();
        lblMakSumma = new javax.swing.JLabel();
        txtMakSumma = new javax.swing.JTextField();
        lblMakVeids = new javax.swing.JLabel();
        cmbMakVeids = new javax.swing.JComboBox<String>();
        btnMakRekistr = new javax.swing.JButton();
        tblMak = new javax.swing.JTable();
        btnMakDzest = new javax.swing.JButton();
        btnMakAtjaunot = new javax.swing.JButton();
        btnMakAizvert = new javax.swing.JButton();

        lblMakVirsraksts.setText("Maksājumu uzskaite");
        lblMakRezervacija.setText("Rezervācijas ID:");
        lblMakSumma.setText("Summa (EUR):");
        lblMakVeids.setText("Maksājuma veids:");
        btnMakRekistr.setText("Reģistrēt maksājumu");
        btnMakRekistr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakRekistrActionPerformed(evt);
            }
        });
        tblMak.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID",
                "Rezervācijas ID",
                "Klients",
                "Summa (EUR)",
                "Veids",
                "Datums"
            }
        ));
        btnMakDzest.setText("Dzēst maksājumu (admins)");
        btnMakDzest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakDzestActionPerformed(evt);
            }
        });
        btnMakAtjaunot.setText("Atjaunot sarakstu");
        btnMakAtjaunot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakAtjaunotActionPerformed(evt);
            }
        });
        btnMakAizvert.setText("Aizvērt");
        btnMakAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMakAizvertActionPerformed(evt);
            }
        });
        scpMak.setViewportView(tblMak);

        javax.swing.GroupLayout dlgMaksajumiLayout = new javax.swing.GroupLayout(dlgMaksajumi.getContentPane());
        dlgMaksajumi.getContentPane().setLayout(dlgMaksajumiLayout);
        dlgMaksajumiLayout.setHorizontalGroup(
            dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblMakVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblMakInfo)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblMakRezervacija)
            .addGap(10, 10, 10)
            .addComponent(txtMakRezervacija, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(lblMakSumma)
            .addGap(10, 10, 10)
            .addComponent(txtMakSumma, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblMakVeids)
            .addGap(10, 10, 10)
            .addComponent(cmbMakVeids, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnMakRekistr)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpMak, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnMakDzest)
            .addGap(10, 10, 10)
            .addComponent(btnMakAtjaunot)
            .addGap(20, 20, 20))
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnMakAizvert)
            .addGap(20, 20, 20))
        );
        dlgMaksajumiLayout.setVerticalGroup(
            dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgMaksajumiLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblMakVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addComponent(lblMakInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblMakRezervacija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtMakRezervacija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(lblMakSumma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtMakSumma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblMakVeids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(cmbMakVeids, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnMakRekistr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(scpMak, javax.swing.GroupLayout.PREFERRED_SIZE, 230, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addGroup(dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnMakDzest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnMakAtjaunot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgMaksajumiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnMakAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgMaksajumi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgMaksajumi.setTitle("Maksājumu uzskaite");

        dlgVesture = new javax.swing.JDialog();

        scpVes = new javax.swing.JScrollPane();
        lblVesVirsraksts = new javax.swing.JLabel();
        lblVesStatuss = new javax.swing.JLabel();
        cmbVesStatuss = new javax.swing.JComboBox<String>();
        btnVesRadit = new javax.swing.JButton();
        tblVes = new javax.swing.JTable();
        lblVesKopsavilkums = new javax.swing.JLabel();
        btnVesAizvert = new javax.swing.JButton();

        lblVesVirsraksts.setText("Rezervāciju vēsture");
        lblVesStatuss.setText("Statuss:");
        btnVesRadit.setText("Rādīt");
        btnVesRadit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVesRaditActionPerformed(evt);
            }
        });
        tblVes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID",
                "Ceļojums",
                "Personas",
                "Kopsumma (EUR)",
                "Statuss",
                "Izveidota",
                "Apmaksāts (EUR)"
            }
        ));
        lblVesKopsavilkums.setText("Kopā rezervāciju: 0");
        btnVesAizvert.setText("Aizvērt");
        btnVesAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVesAizvertActionPerformed(evt);
            }
        });
        scpVes.setViewportView(tblVes);

        javax.swing.GroupLayout dlgVestureLayout = new javax.swing.GroupLayout(dlgVesture.getContentPane());
        dlgVesture.getContentPane().setLayout(dlgVestureLayout);
        dlgVestureLayout.setHorizontalGroup(
            dlgVestureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblVesVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblVesStatuss)
            .addGap(10, 10, 10)
            .addComponent(cmbVesStatuss, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnVesRadit)
            .addGap(20, 20, 20))
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpVes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblVesKopsavilkums)
            .addGap(20, 20, 20))
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnVesAizvert)
            .addGap(20, 20, 20))
        );
        dlgVestureLayout.setVerticalGroup(
            dlgVestureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgVestureLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblVesVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgVestureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblVesStatuss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(cmbVesStatuss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnVesRadit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(scpVes, javax.swing.GroupLayout.PREFERRED_SIZE, 280, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addComponent(lblVesKopsavilkums, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(dlgVestureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnVesAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgVesture.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgVesture.setTitle("Rezervāciju vēsture");

        dlgKlienti = new javax.swing.JDialog();

        scpKli = new javax.swing.JScrollPane();
        lblKliVirsraksts = new javax.swing.JLabel();
        lblKliMeklesana = new javax.swing.JLabel();
        txtKliMeklesana = new javax.swing.JTextField();
        btnKliMeklet = new javax.swing.JButton();
        btnKliVisi = new javax.swing.JButton();
        tblKli = new javax.swing.JTable();
        btnKliDzest = new javax.swing.JButton();
        btnKliAtjaunot = new javax.swing.JButton();
        btnKliAizvert = new javax.swing.JButton();

        lblKliVirsraksts.setText("Klientu saraksts");
        lblKliMeklesana.setText("Meklēt:");
        btnKliMeklet.setText("Meklēt");
        btnKliMeklet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKliMekletActionPerformed(evt);
            }
        });
        btnKliVisi.setText("Rādīt visus");
        btnKliVisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKliVisiActionPerformed(evt);
            }
        });
        tblKli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID",
                "Vārds",
                "Uzvārds",
                "E-pasts",
                "Tālrunis",
                "Lietotājvārds",
                "Loma"
            }
        ));
        btnKliDzest.setText("Dzēst izvēlēto klientu");
        btnKliDzest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKliDzestActionPerformed(evt);
            }
        });
        btnKliAtjaunot.setText("Atjaunot sarakstu");
        btnKliAtjaunot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKliAtjaunotActionPerformed(evt);
            }
        });
        btnKliAizvert.setText("Aizvērt");
        btnKliAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKliAizvertActionPerformed(evt);
            }
        });
        scpKli.setViewportView(tblKli);

        javax.swing.GroupLayout dlgKlientiLayout = new javax.swing.GroupLayout(dlgKlienti.getContentPane());
        dlgKlienti.getContentPane().setLayout(dlgKlientiLayout);
        dlgKlientiLayout.setHorizontalGroup(
            dlgKlientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblKliVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblKliMeklesana)
            .addGap(10, 10, 10)
            .addComponent(txtKliMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnKliMeklet)
            .addGap(10, 10, 10)
            .addComponent(btnKliVisi)
            .addGap(20, 20, 20))
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpKli, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKliDzest)
            .addGap(10, 10, 10)
            .addComponent(btnKliAtjaunot)
            .addGap(20, 20, 20))
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKliAizvert)
            .addGap(20, 20, 20))
        );
        dlgKlientiLayout.setVerticalGroup(
            dlgKlientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgKlientiLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblKliVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addGroup(dlgKlientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(lblKliMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(txtKliMeklesana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKliMeklet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKliVisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addComponent(scpKli, javax.swing.GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addGroup(dlgKlientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKliDzest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnKliAtjaunot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(dlgKlientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKliAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgKlienti.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgKlienti.setTitle("Klientu saraksts");

        dlgPalidziba = new javax.swing.JDialog();

        scpPal = new javax.swing.JScrollPane();
        lblPalVirsraksts = new javax.swing.JLabel();
        txtaPalidziba = new javax.swing.JTextArea();
        btnPalAizvert = new javax.swing.JButton();

        lblPalVirsraksts.setText("Palīdzība — kā lietot programmu");
        btnPalAizvert.setText("Aizvērt");
        btnPalAizvert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPalAizvertActionPerformed(evt);
            }
        });
        scpPal.setViewportView(txtaPalidziba);

        javax.swing.GroupLayout dlgPalidzibaLayout = new javax.swing.GroupLayout(dlgPalidziba.getContentPane());
        dlgPalidziba.getContentPane().setLayout(dlgPalidzibaLayout);
        dlgPalidzibaLayout.setHorizontalGroup(
            dlgPalidzibaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgPalidzibaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblPalVirsraksts)
            .addGap(20, 20, 20))
          .addGroup(dlgPalidzibaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(scpPal, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGap(20, 20, 20))
          .addGroup(dlgPalidzibaLayout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnPalAizvert)
            .addGap(20, 20, 20))
        );
        dlgPalidzibaLayout.setVerticalGroup(
            dlgPalidzibaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(dlgPalidzibaLayout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblPalVirsraksts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addComponent(scpPal, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
            .addGap(10, 10, 10)
            .addGroup(dlgPalidzibaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnPalAizvert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        dlgPalidziba.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        dlgPalidziba.setTitle("Palīdzība (Help)");

        lblTitulis = new javax.swing.JLabel();
        lblStatuss = new javax.swing.JLabel();
        btnPieteiksanas = new javax.swing.JButton();
        btnRegistracija = new javax.swing.JButton();
        btnKatalogs = new javax.swing.JButton();
        btnRezervacijas = new javax.swing.JButton();
        btnMaksajumi = new javax.swing.JButton();
        btnVesture = new javax.swing.JButton();
        btnKlienti = new javax.swing.JButton();
        btnPalidziba = new javax.swing.JButton();

        lblTitulis.setText("Ceļojumu aģentūras sistēma");
        lblStatuss.setText("Nav pieslēgties — pieslēdzieties vai reģistrējieties");
        btnPieteiksanas.setText("Pieteikšanās sistēmā");
        btnPieteiksanas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPieteiksanasActionPerformed(evt);
            }
        });
        btnRegistracija.setText("Jauna klienta reģistrācija");
        btnRegistracija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistracijaActionPerformed(evt);
            }
        });
        btnKatalogs.setText("Ceļojumu katalogs");
        btnKatalogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKatalogsActionPerformed(evt);
            }
        });
        btnRezervacijas.setText("Rezervācijas");
        btnRezervacijas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRezervacijasActionPerformed(evt);
            }
        });
        btnMaksajumi.setText("Maksājumu uzskaite");
        btnMaksajumi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaksajumiActionPerformed(evt);
            }
        });
        btnVesture.setText("Rezervāciju vēsture");
        btnVesture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVestureActionPerformed(evt);
            }
        });
        btnKlienti.setText("Klientu saraksts (admins)");
        btnKlienti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKlientiActionPerformed(evt);
            }
        });
        btnPalidziba.setText("Palīdzība (Help)");
        btnPalidziba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPalidzibaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblTitulis)
            .addGap(20, 20, 20))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(lblStatuss)
            .addGap(20, 20, 20))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnPieteiksanas, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnRegistracija, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKatalogs, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnRezervacijas, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnMaksajumi, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnVesture, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
          .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addComponent(btnKlienti, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(btnPalidziba, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(16, 16, 16)
            .addComponent(lblTitulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(14, 14, 14)
            .addComponent(lblStatuss, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnPieteiksanas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRegistracija, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKatalogs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnRezervacijas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnMaksajumi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnVesture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(btnKlienti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(btnPalidziba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGap(16, 16, 16))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ceļojumu aģentūras sistēma — galvenais logs");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPieteiksanasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPieteiksanasActionPerformed
        atvertPieteiksanos();
    }//GEN-LAST:event_btnPieteiksanasActionPerformed

    private void btnRegistracijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistracijaActionPerformed
        atvertRegistraciju();
    }//GEN-LAST:event_btnRegistracijaActionPerformed

    private void btnKatalogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatalogsActionPerformed
        atvertKatalogu();
    }//GEN-LAST:event_btnKatalogsActionPerformed

    private void btnRezervacijasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezervacijasActionPerformed
        atvertRezervacijas();
    }//GEN-LAST:event_btnRezervacijasActionPerformed

    private void btnMaksajumiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaksajumiActionPerformed
        atvertMaksajumus();
    }//GEN-LAST:event_btnMaksajumiActionPerformed

    private void btnVestureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVestureActionPerformed
        atvertVesture();
    }//GEN-LAST:event_btnVestureActionPerformed

    private void btnKlientiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlientiActionPerformed
        atvertKlientus();
    }//GEN-LAST:event_btnKlientiActionPerformed

    private void btnPalidzibaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPalidzibaActionPerformed
        atvertPalidzibu();
    }//GEN-LAST:event_btnPalidzibaActionPerformed

    private void miPieteiksanasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPieteiksanasActionPerformed
        atvertPieteiksanos();
    }//GEN-LAST:event_miPieteiksanasActionPerformed

    private void miRegistracijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRegistracijaActionPerformed
        atvertRegistraciju();
    }//GEN-LAST:event_miRegistracijaActionPerformed

    private void miKatalogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miKatalogsActionPerformed
        atvertKatalogu();
    }//GEN-LAST:event_miKatalogsActionPerformed

    private void miRezervacijasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRezervacijasActionPerformed
        atvertRezervacijas();
    }//GEN-LAST:event_miRezervacijasActionPerformed

    private void miMaksajumiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miMaksajumiActionPerformed
        atvertMaksajumus();
    }//GEN-LAST:event_miMaksajumiActionPerformed

    private void miVestureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVestureActionPerformed
        atvertVesture();
    }//GEN-LAST:event_miVestureActionPerformed

    private void miIzrakstitiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miIzrakstitiesActionPerformed
        izrakstities();
    }//GEN-LAST:event_miIzrakstitiesActionPerformed

    private void miKlientiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miKlientiActionPerformed
        atvertKlientus();
    }//GEN-LAST:event_miKlientiActionPerformed

    private void miPalidzibaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPalidzibaActionPerformed
        atvertPalidzibu();
    }//GEN-LAST:event_miPalidzibaActionPerformed

    private void miParProgrammuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miParProgrammuActionPerformed
        parProgrammu();
    }//GEN-LAST:event_miParProgrammuActionPerformed

    private void btnPieIeietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPieIeietActionPerformed
        pieteikties();
    }//GEN-LAST:event_btnPieIeietActionPerformed

    private void btnPieAtceltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPieAtceltActionPerformed
        dlgPieteiksanas.dispose();
    }//GEN-LAST:event_btnPieAtceltActionPerformed

    private void btnRegSaglabatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegSaglabatActionPerformed
        registretKlientu();
    }//GEN-LAST:event_btnRegSaglabatActionPerformed

    private void btnRegAtceltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegAtceltActionPerformed
        notiritRegistracijasLaukus();
                dlgRegistracija.dispose();
    }//GEN-LAST:event_btnRegAtceltActionPerformed

    private void btnKatMekletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatMekletActionPerformed
        ieladetKatalogu(true);
    }//GEN-LAST:event_btnKatMekletActionPerformed

    private void btnKatVisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatVisiActionPerformed
        notiritKatMeklesanu();
                ieladetKatalogu(false);
    }//GEN-LAST:event_btnKatVisiActionPerformed

    private void btnKatPievienotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatPievienotActionPerformed
        atvertCelojumaFormu(-1);
    }//GEN-LAST:event_btnKatPievienotActionPerformed

    private void btnKatLabotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatLabotActionPerformed
        Integer labosanasId = izveletaisId(tblKat);
                if (labosanasId != null) {
                    atvertCelojumaFormu(labosanasId);
                }
    }//GEN-LAST:event_btnKatLabotActionPerformed

    private void btnKatDzestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatDzestActionPerformed
        dzestIzveletoCelojumu();
    }//GEN-LAST:event_btnKatDzestActionPerformed

    private void btnKatAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKatAizvertActionPerformed
        dlgKatalogs.dispose();
    }//GEN-LAST:event_btnKatAizvertActionPerformed

    private void btnCfSaglabatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCfSaglabatActionPerformed
        saglabatCelojumu();
    }//GEN-LAST:event_btnCfSaglabatActionPerformed

    private void btnCfAtceltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCfAtceltActionPerformed
        dlgCelojums.dispose();
    }//GEN-LAST:event_btnCfAtceltActionPerformed

    private void btnRezPievienotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezPievienotActionPerformed
        izveidotRezervaciju();
    }//GEN-LAST:event_btnRezPievienotActionPerformed

    private void btnRezAtceltRezActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezAtceltRezActionPerformed
        atceltRezervaciju();
    }//GEN-LAST:event_btnRezAtceltRezActionPerformed

    private void btnRezDzestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezDzestActionPerformed
        dzestRezervaciju();
    }//GEN-LAST:event_btnRezDzestActionPerformed

    private void btnRezAtjaunotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezAtjaunotActionPerformed
        ieladetRezervacijas();
    }//GEN-LAST:event_btnRezAtjaunotActionPerformed

    private void btnRezAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRezAizvertActionPerformed
        dlgRezervacijas.dispose();
    }//GEN-LAST:event_btnRezAizvertActionPerformed

    private void btnMakRekistrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakRekistrActionPerformed
        registretMaksajumu();
    }//GEN-LAST:event_btnMakRekistrActionPerformed

    private void btnMakDzestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakDzestActionPerformed
        dzestMaksajumu();
    }//GEN-LAST:event_btnMakDzestActionPerformed

    private void btnMakAtjaunotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakAtjaunotActionPerformed
        ieladetMaksajumus();
    }//GEN-LAST:event_btnMakAtjaunotActionPerformed

    private void btnMakAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMakAizvertActionPerformed
        dlgMaksajumi.dispose();
    }//GEN-LAST:event_btnMakAizvertActionPerformed

    private void btnVesRaditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVesRaditActionPerformed
        ieladetVesture();
    }//GEN-LAST:event_btnVesRaditActionPerformed

    private void btnVesAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVesAizvertActionPerformed
        dlgVesture.dispose();
    }//GEN-LAST:event_btnVesAizvertActionPerformed

    private void btnKliMekletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKliMekletActionPerformed
        ieladetKlientus(true);
    }//GEN-LAST:event_btnKliMekletActionPerformed

    private void btnKliVisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKliVisiActionPerformed
        notiritKliMeklesanu();
                ieladetKlientus(false);
    }//GEN-LAST:event_btnKliVisiActionPerformed

    private void btnKliDzestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKliDzestActionPerformed
        dzestKlientu();
    }//GEN-LAST:event_btnKliDzestActionPerformed

    private void btnKliAtjaunotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKliAtjaunotActionPerformed
        ieladetKlientus(false);
    }//GEN-LAST:event_btnKliAtjaunotActionPerformed

    private void btnKliAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKliAizvertActionPerformed
        dlgKlienti.dispose();
    }//GEN-LAST:event_btnKliAizvertActionPerformed

    private void btnPalAizvertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPalAizvertActionPerformed
        dlgPalidziba.dispose();
    }//GEN-LAST:event_btnPalAizvertActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        izietNoProgrammas();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCfAtcelt;
    private javax.swing.JButton btnCfSaglabat;
    private javax.swing.JButton btnKatAizvert;
    private javax.swing.JButton btnKatDzest;
    private javax.swing.JButton btnKatLabot;
    private javax.swing.JButton btnKatMeklet;
    private javax.swing.JButton btnKatPievienot;
    private javax.swing.JButton btnKatVisi;
    private javax.swing.JButton btnKatalogs;
    private javax.swing.JButton btnKliAizvert;
    private javax.swing.JButton btnKliAtjaunot;
    private javax.swing.JButton btnKliDzest;
    private javax.swing.JButton btnKliMeklet;
    private javax.swing.JButton btnKliVisi;
    private javax.swing.JButton btnKlienti;
    private javax.swing.JButton btnMakAizvert;
    private javax.swing.JButton btnMakAtjaunot;
    private javax.swing.JButton btnMakDzest;
    private javax.swing.JButton btnMakRekistr;
    private javax.swing.JButton btnMaksajumi;
    private javax.swing.JButton btnPalAizvert;
    private javax.swing.JButton btnPalidziba;
    private javax.swing.JButton btnPieAtcelt;
    private javax.swing.JButton btnPieIeiet;
    private javax.swing.JButton btnPieteiksanas;
    private javax.swing.JButton btnRegAtcelt;
    private javax.swing.JButton btnRegSaglabat;
    private javax.swing.JButton btnRegistracija;
    private javax.swing.JButton btnRezAizvert;
    private javax.swing.JButton btnRezAtceltRez;
    private javax.swing.JButton btnRezAtjaunot;
    private javax.swing.JButton btnRezDzest;
    private javax.swing.JButton btnRezPievienot;
    private javax.swing.JButton btnRezervacijas;
    private javax.swing.JButton btnVesAizvert;
    private javax.swing.JButton btnVesRadit;
    private javax.swing.JButton btnVesture;
    private javax.swing.JComboBox<String> cmbMakVeids;
    private javax.swing.JComboBox<String> cmbRezCelojums;
    private javax.swing.JComboBox<String> cmbRezKlients;
    private javax.swing.JComboBox<String> cmbVesStatuss;
    private javax.swing.JDialog dlgCelojums;
    private javax.swing.JDialog dlgKatalogs;
    private javax.swing.JDialog dlgKlienti;
    private javax.swing.JDialog dlgMaksajumi;
    private javax.swing.JDialog dlgPalidziba;
    private javax.swing.JDialog dlgPieteiksanas;
    private javax.swing.JDialog dlgRegistracija;
    private javax.swing.JDialog dlgRezervacijas;
    private javax.swing.JDialog dlgVesture;
    private javax.swing.JLabel lblCfApraksts;
    private javax.swing.JLabel lblCfBeigas;
    private javax.swing.JLabel lblCfCena;
    private javax.swing.JLabel lblCfGalamerkis;
    private javax.swing.JLabel lblCfNosaukums;
    private javax.swing.JLabel lblCfSakums;
    private javax.swing.JLabel lblCfVietas;
    private javax.swing.JLabel lblCfVirsraksts;
    private javax.swing.JLabel lblKatInfo;
    private javax.swing.JLabel lblKatMeklesana;
    private javax.swing.JLabel lblKatVirsraksts;
    private javax.swing.JLabel lblKliMeklesana;
    private javax.swing.JLabel lblKliVirsraksts;
    private javax.swing.JLabel lblMakInfo;
    private javax.swing.JLabel lblMakRezervacija;
    private javax.swing.JLabel lblMakSumma;
    private javax.swing.JLabel lblMakVeids;
    private javax.swing.JLabel lblMakVirsraksts;
    private javax.swing.JLabel lblPalVirsraksts;
    private javax.swing.JLabel lblPieLietotajvards;
    private javax.swing.JLabel lblPieParole;
    private javax.swing.JLabel lblPieVirsraksts;
    private javax.swing.JLabel lblRegEpasts;
    private javax.swing.JLabel lblRegLietotajvards;
    private javax.swing.JLabel lblRegParole;
    private javax.swing.JLabel lblRegParole2;
    private javax.swing.JLabel lblRegTalrunis;
    private javax.swing.JLabel lblRegUzvards;
    private javax.swing.JLabel lblRegVards;
    private javax.swing.JLabel lblRegVirsraksts;
    private javax.swing.JLabel lblRezCelojums;
    private javax.swing.JLabel lblRezKlients;
    private javax.swing.JLabel lblRezPersonas;
    private javax.swing.JLabel lblRezVirsraksts;
    private javax.swing.JLabel lblStatuss;
    private javax.swing.JLabel lblTitulis;
    private javax.swing.JLabel lblVesKopsavilkums;
    private javax.swing.JLabel lblVesStatuss;
    private javax.swing.JLabel lblVesVirsraksts;
    private javax.swing.JMenu menuDarbibas;
    private javax.swing.JMenuBar menuJosla;
    private javax.swing.JMenu menuPalidziba;
    private javax.swing.JMenuItem miIzrakstities;
    private javax.swing.JMenuItem miKatalogs;
    private javax.swing.JMenuItem miKlienti;
    private javax.swing.JMenuItem miMaksajumi;
    private javax.swing.JMenuItem miPalidziba;
    private javax.swing.JMenuItem miParProgrammu;
    private javax.swing.JMenuItem miPieteiksanas;
    private javax.swing.JMenuItem miRegistracija;
    private javax.swing.JMenuItem miRezervacijas;
    private javax.swing.JMenuItem miVesture;
    private javax.swing.JPasswordField pwdPieParole;
    private javax.swing.JPasswordField pwdRegParole;
    private javax.swing.JPasswordField pwdRegParole2;
    private javax.swing.JScrollPane scpKat;
    private javax.swing.JScrollPane scpKli;
    private javax.swing.JScrollPane scpMak;
    private javax.swing.JScrollPane scpPal;
    private javax.swing.JScrollPane scpRez;
    private javax.swing.JScrollPane scpVes;
    private javax.swing.JTable tblKat;
    private javax.swing.JTable tblKli;
    private javax.swing.JTable tblMak;
    private javax.swing.JTable tblRez;
    private javax.swing.JTable tblVes;
    private javax.swing.JTextField txtCfApraksts;
    private javax.swing.JTextField txtCfBeigas;
    private javax.swing.JTextField txtCfCena;
    private javax.swing.JTextField txtCfGalamerkis;
    private javax.swing.JTextField txtCfNosaukums;
    private javax.swing.JTextField txtCfSakums;
    private javax.swing.JTextField txtCfVietas;
    private javax.swing.JTextField txtKatMeklesana;
    private javax.swing.JTextField txtKliMeklesana;
    private javax.swing.JTextField txtMakRezervacija;
    private javax.swing.JTextField txtMakSumma;
    private javax.swing.JTextField txtPieLietotajvards;
    private javax.swing.JTextField txtRegEpasts;
    private javax.swing.JTextField txtRegLietotajvards;
    private javax.swing.JTextField txtRegTalrunis;
    private javax.swing.JTextField txtRegUzvards;
    private javax.swing.JTextField txtRegVards;
    private javax.swing.JTextField txtRezPersonas;
    private javax.swing.JTextArea txtaPalidziba;
    // End of variables declaration//GEN-END:variables
}

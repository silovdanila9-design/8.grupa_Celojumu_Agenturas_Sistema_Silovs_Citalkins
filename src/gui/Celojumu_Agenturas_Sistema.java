/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import dao.CelojumsDAO;
import dao.KlientsDAO;
import model.Celojums;
import model.Klients;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Danils.Silovs
 */
public class Celojumu_Agenturas_Sistema extends javax.swing.JFrame {

    /**
     * Creates new form Celojumu_Agenturas_Sistema
     */
    public Celojumu_Agenturas_Sistema() {
        // initComponents satur GUI Builder ģenerēto kodu - ja kaut kas tur neizdodas
        // (piem. attēla ielāde), mēs to noķeram, lai GUI joprojām atvērtos
        try {
            initComponents();
        } catch (Throwable t) {
            System.err.println("Brīdinājums: kļūda inicializējot GUI komponentus: " + t.getMessage());
            t.printStackTrace();
        }
        try {
            applyStyling();
        } catch (Throwable t) {
            System.err.println("Brīdinājums: kļūda pielietojot stilu: " + t.getMessage());
        }
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        // Datubāzes sāknēšana notiek fonā - ja tā neizdodas, GUI joprojām atveras
        try {
            new dao.KlientsDAO().printAllUsers();
        } catch (Throwable t) {
            System.err.println("Brīdinājums: neizdevās pieslēgties datubāzei: " + t.getMessage());
        }
        setLocationRelativeTo(null);
        // Reģistrējam loga aizvēršanas klausītāju, lai strādātu formWindowClosing()
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }

    /**
     * Pievieno krāsas un fona attēlu visiem logiem un komponentiem.
     * Izmantotā palete saskan ar augšupielādēto lidmašīnas attēlu
     * (tumši zils / violets / zelta akcenti).
     */
    private void applyStyling() {
        // Krāsu palete
        java.awt.Color deepBlue   = new java.awt.Color(15, 23, 60);
        java.awt.Color purple     = new java.awt.Color(74, 38, 124);
        java.awt.Color accentGold = new java.awt.Color(245, 197, 66);
        java.awt.Color buttonBg   = new java.awt.Color(52, 73, 145);
        java.awt.Color dangerRed  = new java.awt.Color(220, 53, 69);
        java.awt.Color neutralGray= new java.awt.Color(108, 117, 125);
        java.awt.Color whiteText  = java.awt.Color.WHITE;
        java.awt.Color tableBg    = new java.awt.Color(245, 247, 255);

        // ---- Galvenais JFrame (pieteikšanās logs) ----
        // jPanel1 jau ir ImageBackgroundPanel (skatīties initComponents)
        // Iekrāsojam virskomponentus
        jLabel1.setForeground(whiteText);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));

        styleButton(Loginbtn, accentGold, deepBlue);
        styleButton(Registrationbtn, buttonBg, whiteText);

        // ---- JDialog (ceļojumu saraksta logs) ----
        // jPanel2 jau ir ImageBackgroundPanel (skatīties initComponents)
        jLabelWelcome.setForeground(whiteText);
        jLabelWelcome.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));

        // Tabulas stils
        jTable1.setBackground(tableBg);
        jTable1.setForeground(deepBlue);
        jTable1.setGridColor(new java.awt.Color(200, 210, 230));
        jTable1.setRowHeight(26);
        jTable1.setSelectionBackground(accentGold);
        jTable1.setSelectionForeground(deepBlue);
        jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        jTable1.getTableHeader().setBackground(deepBlue);
        jTable1.getTableHeader().setForeground(whiteText);
        jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));

        // Pogas
        styleButton(Reservbtn, accentGold, deepBlue);
        styleButton(Deletebtn, dangerRed, whiteText);
        styleButton(Updatebtn, buttonBg, whiteText);
        styleButton(Logoutbtn, neutralGray, whiteText);
        styleButton(Searchbtn, accentGold, deepBlue);

        // JDialog virsrakums / fona nokrāsa
        jDialog1.setTitle("Ceļojumu aģentūra - lietotāja panelis");
        jDialog1.getContentPane().setBackground(deepBlue);

        // Galvenā JFrame virsrakums un fona nokrāsa (rezerve, ja attēls nav atrasts)
        setTitle("Ceļojumu aģentūras sistēma");
        getContentPane().setBackground(deepBlue);
    }

    /**
     * Palīgmetode pogu vizuālajam noformējumam.
     */
    private void styleButton(javax.swing.JButton btn, java.awt.Color bg, java.awt.Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        // Nedaudz palielinām pogas iekšējo atstarpi, lai tās izskatās modernāk
        btn.setMargin(new java.awt.Insets(8, 16, 8, 16));
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {
    int result = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Vai tiešām vēlaties iziet?",
        "Apstiprinājums",
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE
    );
    
    if (result == javax.swing.JOptionPane.YES_OPTION) {
        System.exit(0);
    }
}

    private String aktivaLietotajvards = "";
    
    private void loadCelojumiData() {
        String[] columnNames = {"ID", "Nosaukums", "Apraksts", "Cena (EUR)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        try {
            List<Celojums> saraksts = CelojumsDAO.getAllCelojumi();
            for (Celojums c : saraksts) {
                Object[] row = {
                    c.getId(),
                    c.getNosaukums(),
                    c.getApraksts(),
                    c.getCena()
                };
                model.addRow(row);
            }
            jTable1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Kļūda ielādējot datus no datubāzes: " + e.getMessage(), 
                "Kļūda", 
                JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel2 = new gui.ImageBackgroundPanel(gui.ImageBackgroundPanel.loadImage("/resources/celojums_bg.jpg"));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Reservbtn = new javax.swing.JButton();
        Deletebtn = new javax.swing.JButton();
        Updatebtn = new javax.swing.JButton();
        Logoutbtn = new javax.swing.JButton();
        jLabelWelcome = new javax.swing.JLabel();
        Searchbtn = new javax.swing.JButton();
        jPanel1 = new gui.ImageBackgroundPanel(gui.ImageBackgroundPanel.loadImage("/resources/celojums_bg.jpg"));
        jLabel1 = new javax.swing.JLabel();
        Loginbtn = new javax.swing.JButton();
        Registrationbtn = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nosaukums", "Apraksts", "Cena (EUR)"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        Reservbtn.setText("Rezervēt izvēlēto ceļojumu");
        Reservbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservbtnActionPerformed(evt);
            }
        });

        Deletebtn.setText("Dzēst coļojumu");
        Deletebtn.setToolTipText("");
        Deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletebtnActionPerformed(evt);
            }
        });

        Updatebtn.setText("Atsvaidzināt sarakstu");
        Updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdatebtnActionPerformed(evt);
            }
        });

        Logoutbtn.setText("Izrakstīties");
        Logoutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutbtnActionPerformed(evt);
            }
        });

        Searchbtn.setText("Meklēt");
        Searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(Deletebtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(Reservbtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Logoutbtn)
                        .addGap(78, 78, 78))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Updatebtn)
                        .addGap(49, 49, 49))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(jLabelWelcome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Searchbtn)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(Searchbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Reservbtn)
                    .addComponent(Updatebtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Logoutbtn)
                    .addComponent(Deletebtn))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Ceļojumu aģentūras sistēma");

        Loginbtn.setText("Ielogoties");
        Loginbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginbtnActionPerformed(evt);
            }
        });

        Registrationbtn.setText("Reģistrēties");
        Registrationbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrationbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Registrationbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Loginbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addComponent(Loginbtn)
                .addGap(51, 51, 51)
                .addComponent(Registrationbtn)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        Loginbtn.getAccessibleContext().setAccessibleName("Loginbtn");
        Registrationbtn.getAccessibleContext().setAccessibleName("Registrationbtn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LoginbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginbtnActionPerformed
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 10, 10));
        
        javax.swing.JTextField usernameField = new javax.swing.JTextField();
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField();

        panel.add(new javax.swing.JLabel("Lietotājvārds:"));
        panel.add(usernameField);
        panel.add(new javax.swing.JLabel("Parole:"));
        panel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(
                this, 
                panel, 
                "Sistēmas pieteikšanās", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String lietotajvards = usernameField.getText().trim();
            String parole = new String(passwordField.getPassword());

            if (lietotajvards.isEmpty() || parole.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lūdzu, aizpildiet visus laukus!", "Kļūda", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KlientsDAO klientsDAO = new KlientsDAO();
            Klients klients = klientsDAO.login(lietotajvards, parole);

            if (klients != null) {
                aktivaLietotajvards = lietotajvards;
                JOptionPane.showMessageDialog(this, "Ieja ir veiksmīga!", "Paziņojums", JOptionPane.INFORMATION_MESSAGE);

                // Sagatavo un atver ceļojumu logu normālā izmērā
                jLabelWelcome.setText("Laipni lūdzam, " + aktivaLietotajvards + "! Izvēlieties ceļojumu rezervācijai:");
                loadCelojumiData();
                
                this.setVisible(false);
                jDialog1.setSize(850, 600); // Uzstādām normālu loga izmēru
                jDialog1.setLocationRelativeTo(null); // Centrējam uz ekrāna
                jDialog1.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nepareizs lietotājvārds vai parole!", "Kļūda", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_LoginbtnActionPerformed

    private void RegistrationbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrationbtnActionPerformed
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(5, 2, 10, 10));
        
        javax.swing.JTextField vardsField = new javax.swing.JTextField();
        javax.swing.JTextField uzvardsField = new javax.swing.JTextField();
        javax.swing.JTextField epastsField = new javax.swing.JTextField();
        javax.swing.JTextField lietotajvardsField = new javax.swing.JTextField();
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField();

        panel.add(new javax.swing.JLabel("Vārds:"));
        panel.add(vardsField);
        panel.add(new javax.swing.JLabel("Uzvārds:"));
        panel.add(uzvardsField);
        panel.add(new javax.swing.JLabel("E-pasts:"));
        panel.add(epastsField);
        panel.add(new javax.swing.JLabel("Lietotājvārds:"));
        panel.add(lietotajvardsField);
        panel.add(new javax.swing.JLabel("Parole:"));
        panel.add(passwordField);

        // Parāda dialoglodziņu ar visiem ievades laukiem vienuviet
        int option = JOptionPane.showConfirmDialog(
                this, 
                panel, 
                "Jauna lietotāja reģistrācija", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String vards = vardsField.getText().trim();
            String uzvards = uzvardsField.getText().trim();
            String epasts = epastsField.getText().trim();
            String lietotajvards = lietotajvardsField.getText().trim();
            String parole = new String(passwordField.getPassword());

            // Datu validācija – pārbaudām, vai visi lauki ir aizpildīti
            if (vards.isEmpty() || uzvards.isEmpty() || epasts.isEmpty() || lietotajvards.isEmpty() || parole.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Visiem laukiem jābūt aizpildītiem!", "Kļūda", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Saglabājam datus, izmantojot Klients modeli un KlientsDAO
            Klients jaunsKlients = new Klients(0, vards, uzvards, epasts, lietotajvards, parole);
            KlientsDAO klientsDAO = new KlientsDAO();
            boolean veiksme = klientsDAO.register(jaunsKlients);

            if (veiksme) {
                JOptionPane.showMessageDialog(this, "Reģistrēšanās veiksmīga! Tagad varat ielogoties.", "Informācija", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Neizdevās reģistrēt lietotāju. Iespējams, šāds lietotājvārds jau eksistē.", "Kļūda", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_RegistrationbtnActionPerformed

    private void ReservbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservbtnActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int celojumsId = (int) jTable1.getValueAt(selectedRow, 0);
            String nosaukums = (String) jTable1.getValueAt(selectedRow, 1);
            
            boolean success = dao.RezervacijaDAO.createRezervacija(celojumsId, aktivaLietotajvards);
            if (success) {
                JOptionPane.showMessageDialog(this, "Ceļojums '" + nosaukums + "' veiksmīgi rezervēts lietotājam " + aktivaLietotajvards + "!");
            } else {
                JOptionPane.showMessageDialog(this, "Neizdevās veikt rezervāciju.", "Kļūda", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lūdzu, izvēlieties ceļojumu no saraksta!", "Brīdinājums", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_ReservbtnActionPerformed

    private void DeletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletebtnActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int celojumsId = (int) jTable1.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Vai tiešām vēlaties dzēst šo ceļojumu?", "Apstiprinājums", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean dzēsts = CelojumsDAO.deleteCelojums(celojumsId);
                if (dzēsts) {
                    JOptionPane.showMessageDialog(this, "Ceļojums veiksmīgi dzēsts!");
                    loadCelojumiData();
                } else {
                    JOptionPane.showMessageDialog(this, "Neizdevās dzēst ceļojumu.", "Kļūda", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lūdzu, izvēlieties ceļojumu dzēšanai!", "Brīdinājums", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_DeletebtnActionPerformed

    private void UpdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdatebtnActionPerformed
        loadCelojumiData();
        JOptionPane.showMessageDialog(this, "Saraksts atsvaidzināts!");
    }//GEN-LAST:event_UpdatebtnActionPerformed

    private void LogoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutbtnActionPerformed
        jDialog1.dispose();
        this.setVisible(true);
    }//GEN-LAST:event_LogoutbtnActionPerformed

    private void SearchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchbtnActionPerformed
        String keyword = JOptionPane.showInputDialog(this, "Ievadiet meklēto atslēgvārdu:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Celojums> rezultati = CelojumsDAO.searchCelojumi(keyword.trim());
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nosaukums", "Apraksts", "Cena (EUR)"}, 0);
            for (Celojums c : rezultati) {
                model.addRow(new Object[]{c.getId(), c.getNosaukums(), c.getApraksts(), c.getCena()});
            }
            jTable1.setModel(model);
        }
    }//GEN-LAST:event_SearchbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Celojumu_Agenturas_Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Celojumu_Agenturas_Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Deletebtn;
    private javax.swing.JButton Loginbtn;
    private javax.swing.JButton Logoutbtn;
    private javax.swing.JButton Registrationbtn;
    private javax.swing.JButton Reservbtn;
    private javax.swing.JButton Searchbtn;
    private javax.swing.JButton Updatebtn;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

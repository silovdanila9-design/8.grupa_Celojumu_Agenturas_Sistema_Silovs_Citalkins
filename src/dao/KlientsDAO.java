package dao;

import db.DatabaseManager;
import model.Klients;
import java.sql.*;

public class KlientsDAO {

    public Klients login(String lietotajvards, String parole) {
        String sql = "SELECT * FROM LIETOTAJS WHERE LIETOTAJAVRDS = ? AND PAROLES_JAUCENKODS = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String hashedPassword = util.PasswordUtil.hashPassword(parole);
            
            pstmt.setString(1, lietotajvards);
            pstmt.setString(2, hashedPassword);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Klients(
                        rs.getInt("ID"),
                        rs.getString("VARDS"),
                        rs.getString("UZVARDS"),
                        rs.getString("EPASTS"),
                        rs.getString("LIETOTAJAVRDS"),
                        rs.getString("PAROLES_JAUCENKODS")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(Klients klients) {
        String sql = "INSERT INTO LIETOTAJS (VARDS, UZVARDS, EPASTS, LIETOTAJAVRDS, PAROLES_JAUCENKODS) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, klients.getVards());
            pstmt.setString(2, klients.getUzvards());
            pstmt.setString(3, klients.getEpasts());
            pstmt.setString(4, klients.getLietotajvards());
            pstmt.setString(5, klients.getParole());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printAllUsers() {
        String sql = "SELECT * FROM LIETOTAJS";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("=== REGISTRĒTIE LIETOTĀJI BĀZĒ ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID") + 
                                   " | Vārds: " + rs.getString("VARDS") + 
                                   " " + rs.getString("UZVARDS") +
                                   " | Lietotājvārds: " + rs.getString("LIETOTAJAVRDS") + 
                                   " | E-pasts: " + rs.getString("EPASTS"));
            }
            System.out.println("==================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
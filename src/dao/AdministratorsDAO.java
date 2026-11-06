/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import db.DatabaseManager;
import java.sql.*;
/**
 *
 * @author Danils.Silovs
 */
public class AdministratorsDAO {
    public boolean loginAdministratoram(String lietotajvards, String parole) {
        String sql = "SELECT paroles_jaucenkods FROM ADMINISTRATORS WHERE lietotajvards = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lietotajvards);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("paroles_jaucenkods");
                    return dbPassword.equals(parole);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

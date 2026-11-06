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
public class RezervacijaDAO {
    
    public static boolean createRezervacija(int celojumsId, String lietotajvards) {
        String sql = "INSERT INTO REZERVACIJA (CELOJUMS_ID, LIETOTAJVARDS) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, celojumsId);
            pstmt.setString(2, lietotajvards);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

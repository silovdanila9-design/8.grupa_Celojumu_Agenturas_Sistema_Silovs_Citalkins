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
public class MaksajumsDAO {
    public static boolean createMaksajums(int rezervacijaId, double summa, String veids) {
        String sql = "INSERT INTO MAKSAJUMS (rezervacija_id, summa, maksajuma_datums, maksajuma_veids) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rezervacijaId);
            pstmt.setDouble(2, summa);
            pstmt.setString(3, veids);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

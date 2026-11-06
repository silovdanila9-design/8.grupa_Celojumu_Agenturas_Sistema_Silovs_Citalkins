/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DatabaseManager;
import model.Celojums;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Danils.Silovs
 */
public class CelojumsDAO {
    
    public static List<Celojums> getAllCelojumi() {
        List<Celojums> saraksts = new ArrayList<>();
        String sql = "SELECT * FROM CELOJUMS";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saraksts.add(new Celojums(
                    rs.getInt("ID"),
                    rs.getString("NOSAUKUMS"),
                    rs.getString("APRAKSTS"),
                    rs.getDouble("CENA")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public static boolean deleteCelojums(int id) {
        String sql = "DELETE FROM CELOJUMS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<Celojums> searchCelojumi(String keyword) {
        List<Celojums> saraksts = new ArrayList<>();
        String sql = "SELECT * FROM CELOJUMS WHERE LOWER(NOSAUKUMS) LIKE ? OR LOWER(APRAKSTS) LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                saraksts.add(new Celojums(
                    rs.getInt("ID"),
                    rs.getString("NOSAUKUMS"),
                    rs.getString("APRAKSTS"),
                    rs.getDouble("CENA")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public static List<Celojums> getCelojumiSortedByPrice(boolean ascending) {
        List<Celojums> saraksts = new ArrayList<>();
        String sql = "SELECT * FROM CELOJUMS ORDER BY CENA " + (ascending ? "ASC" : "DESC");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saraksts.add(new Celojums(
                    rs.getInt("ID"),
                    rs.getString("NOSAUKUMS"),
                    rs.getString("APRAKSTS"),
                    rs.getDouble("CENA")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }
}

package dao;

import db.DatabaseManager;
import model.Lietotajs;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LietotajsDAO {

    public int registret(Lietotajs lietotajs) {
        String sql = "INSERT INTO LIETOTAJS (VARDS, UZVARDS, EPASTS, TALRUNIS, "
                + "LIETOTAJVARDS, PAROLES_HASH, LOMA) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, lietotajs.getVards());
            ps.setString(2, lietotajs.getUzvards());
            ps.setString(3, lietotajs.getEpasts());
            ps.setString(4, lietotajs.getTalrunis());
            ps.setString(5, lietotajs.getLietotajvards());
            ps.setString(6, PasswordUtil.hashPassword(lietotajs.getParolesHash()));
            ps.setString(7, lietotajs.getLoma() == null ? "KLIENTS"
                    : lietotajs.getLoma());
            ps.executeUpdate();

            try (ResultSet atslegas = ps.getGeneratedKeys()) {
                if (atslegas.next()) {
                    return atslegas.getInt(1);
                }
            }
        } catch (SQLException e) {

            if ("23505".equals(e.getSQLState())) {
                System.err.println("Lietotājvārds jau eksistē: "
                        + lietotajs.getLietotajvards());
            } else {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public Lietotajs pieslegties(String lietotajvards, String parole) {
        String sql = "SELECT ID, VARDS, UZVARDS, EPASTS, TALRUNIS, "
                + "LIETOTAJVARDS, PAROLES_HASH, LOMA "
                + "FROM LIETOTAJS WHERE LIETOTAJVARDS = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lietotajvards);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()
                        && PasswordUtil.parbauditParole(parole,
                                rs.getString("PAROLES_HASH"))) {
                    return new Lietotajs(
                            rs.getInt("ID"),
                            rs.getString("VARDS"),
                            rs.getString("UZVARDS"),
                            rs.getString("EPASTS"),
                            rs.getString("TALRUNIS"),
                            rs.getString("LIETOTAJVARDS"),
                            rs.getString("PAROLES_HASH"),
                            rs.getString("LOMA"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean lietotajvardsAiznemts(String lietotajvards) {
        String sql = "SELECT ID FROM LIETOTAJS WHERE LIETOTAJVARDS = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lietotajvards);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Lietotajs> getVisi() {
        List<Lietotajs> saraksts = new ArrayList<>();
        String sql = "SELECT ID, VARDS, UZVARDS, EPASTS, TALRUNIS, "
                + "LIETOTAJVARDS, LOMA FROM LIETOTAJS ORDER BY ID";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saraksts.add(new Lietotajs(
                        rs.getInt("ID"),
                        rs.getString("VARDS"),
                        rs.getString("UZVARDS"),
                        rs.getString("EPASTS"),
                        rs.getString("TALRUNIS"),
                        rs.getString("LIETOTAJVARDS"),
                        null,
                        rs.getString("LOMA")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public List<Lietotajs> mekleli(String atslegvards) {
        List<Lietotajs> saraksts = new ArrayList<>();
        String sql = "SELECT ID, VARDS, UZVARDS, EPASTS, TALRUNIS, "
                + "LIETOTAJVARDS, LOMA FROM LIETOTAJS "
                + "WHERE LOWER(VARDS) LIKE ? OR LOWER(UZVARDS) LIKE ? "
                + "OR LOWER(EPASTS) LIKE ? OR LOWER(LIETOTAJVARDS) LIKE ? "
                + "ORDER BY ID";
        String paraugs = "%" + atslegvards.toLowerCase() + "%";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, paraugs);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    saraksts.add(new Lietotajs(
                            rs.getInt("ID"),
                            rs.getString("VARDS"),
                            rs.getString("UZVARDS"),
                            rs.getString("EPASTS"),
                            rs.getString("TALRUNIS"),
                            rs.getString("LIETOTAJVARDS"),
                            null,
                            rs.getString("LOMA")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public boolean dzest(int id) {
        String sql = "DELETE FROM LIETOTAJS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

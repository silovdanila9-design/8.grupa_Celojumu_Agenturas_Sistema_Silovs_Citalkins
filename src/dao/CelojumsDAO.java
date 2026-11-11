package dao;

import db.DatabaseManager;
import model.Celojums;
import util.Validacija;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CelojumsDAO {

    public List<Celojums> getVisi() {
        return mekli("");
    }

    public List<Celojums> mekli(String atslegvards) {
        List<Celojums> saraksts = new ArrayList<>();
        boolean mekle = Validacija.navTukss(atslegvards);
        String sql = "SELECT ID, NOSAUKUMS, GALAMERKIS, APRAKSTS, "
                + "SAKUMA_DATUMS, BEIGU_DATUMS, CENA, VIETU_SKAITS FROM CELOJUMS";
        if (mekle) {
            sql += " WHERE LOWER(NOSAUKUMS) LIKE ? OR LOWER(GALAMERKIS) LIKE ? "
                    + "OR LOWER(APRAKSTS) LIKE ?";
        }
        sql += " ORDER BY SAKUMA_DATUMS";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (mekle) {
                String paraugs = "%" + atslegvards.trim().toLowerCase() + "%";
                ps.setString(1, paraugs);
                ps.setString(2, paraugs);
                ps.setString(3, paraugs);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    saraksts.add(jaunsNoRindas(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public List<Celojums> kartotPecCenas(boolean augosaSeciba) {
        List<Celojums> saraksts = new ArrayList<>();
        String sql = "SELECT ID, NOSAUKUMS, GALAMERKIS, APRAKSTS, "
                + "SAKUMA_DATUMS, BEIGU_DATUMS, CENA, VIETU_SKAITS FROM CELOJUMS "
                + "ORDER BY CENA " + (augosaSeciba ? "ASC" : "DESC");
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                saraksts.add(jaunsNoRindas(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saraksts;
    }

    public Celojums pecId(int id) {
        String sql = "SELECT ID, NOSAUKUMS, GALAMERKIS, APRAKSTS, "
                + "SAKUMA_DATUMS, BEIGU_DATUMS, CENA, VIETU_SKAITS "
                + "FROM CELOJUMS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return jaunsNoRindas(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int pievienot(Celojums celojums) {
        String sql = "INSERT INTO CELOJUMS (NOSAUKUMS, GALAMERKIS, APRAKSTS, "
                + "SAKUMA_DATUMS, BEIGU_DATUMS, CENA, VIETU_SKAITS) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            aizpilditParametrus(ps, celojums);
            ps.executeUpdate();
            try (ResultSet atslegas = ps.getGeneratedKeys()) {
                if (atslegas.next()) {
                    return atslegas.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean labot(Celojums celojums) {
        String sql = "UPDATE CELOJUMS SET NOSAUKUMS = ?, GALAMERKIS = ?, "
                + "APRAKSTS = ?, SAKUMA_DATUMS = ?, BEIGU_DATUMS = ?, "
                + "CENA = ?, VIETU_SKAITS = ? WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            aizpilditParametrus(ps, celojums);
            ps.setInt(8, celojums.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dzest(int id) throws SQLException {
        String sql = "DELETE FROM CELOJUMS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Celojums jaunsNoRindas(ResultSet rs) throws SQLException {
        return new Celojums(
                rs.getInt("ID"),
                rs.getString("NOSAUKUMS"),
                rs.getString("GALAMERKIS"),
                rs.getString("APRAKSTS"),
                rs.getDate("SAKUMA_DATUMS").toLocalDate(),
                rs.getDate("BEIGU_DATUMS").toLocalDate(),
                rs.getDouble("CENA"),
                rs.getInt("VIETU_SKAITS"));
    }

    private void aizpilditParametrus(PreparedStatement ps, Celojums celojums)
            throws SQLException {
        ps.setString(1, celojums.getNosaukums());
        ps.setString(2, celojums.getGalamerkis());
        ps.setString(3, celojums.getApraksts());
        ps.setDate(4, Date.valueOf(celojums.getSakumaDatums()));
        ps.setDate(5, Date.valueOf(celojums.getBeiguDatums()));
        ps.setDouble(6, celojums.getCena());
        ps.setInt(7, celojums.getVietuSkaits());
    }
}

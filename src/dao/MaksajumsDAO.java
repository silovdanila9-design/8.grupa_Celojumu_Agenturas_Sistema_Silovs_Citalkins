package dao;

import db.DatabaseManager;
import model.Maksajums;
import model.Rezervacija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaksajumsDAO {

    public int pievienot(Maksajums maksajums) {
        String sql = "INSERT INTO MAKSAJUMS (REZERVACIJA_ID, SUMMA, DATUMS, VEIDS) "
                + "VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, maksajums.getRezervacijaId());
            ps.setDouble(2, maksajums.getSumma());
            ps.setString(3, maksajums.getVeids());
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

    public List<Object[]> getTabula(int lietotajaId) {
        List<Object[]> rindas = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT m.ID, m.REZERVACIJA_ID, "
                + "l.VARDS || ' ' || l.UZVARDS AS KLIENTS, m.SUMMA, m.VEIDS, m.DATUMS "
                + "FROM MAKSAJUMS m "
                + "JOIN REZERVACIJA r ON r.ID = m.REZERVACIJA_ID "
                + "JOIN LIETOTAJS l ON l.ID = r.LIETOTAJS_ID ");
        List<Object> parametri = new ArrayList<>();
        if (lietotajaId > 0) {
            sql.append("WHERE r.LIETOTAJS_ID = ? ");
            parametri.add(lietotajaId);
        }
        sql.append("ORDER BY m.ID DESC");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rindas.add(new Object[]{
                        rs.getInt("ID"),
                        rs.getInt("REZERVACIJA_ID"),
                        rs.getString("KLIENTS"),
                        rs.getDouble("SUMMA"),
                        rs.getString("VEIDS"),
                        rs.getTimestamp("DATUMS").toLocalDateTime()
                                .format(java.time.format.DateTimeFormatter
                                        .ofPattern("dd.MM.yyyy HH:mm"))
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rindas;
    }

    public Maksajums pecId(int id) {
        String sql = "SELECT ID, REZERVACIJA_ID, SUMMA, DATUMS, VEIDS "
                + "FROM MAKSAJUMS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Maksajums(
                            rs.getInt("ID"),
                            rs.getInt("REZERVACIJA_ID"),
                            rs.getDouble("SUMMA"),
                            rs.getTimestamp("DATUMS").toLocalDateTime(),
                            rs.getString("VEIDS"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean piederLietotajam(int maksajumaId, int lietotajaId) {
        String sql = "SELECT m.ID FROM MAKSAJUMS m "
                + "JOIN REZERVACIJA r ON r.ID = m.REZERVACIJA_ID "
                + "WHERE m.ID = ? AND r.LIETOTAJS_ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maksajumaId);
            ps.setInt(2, lietotajaId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dzest(int id) {
        String sql = "DELETE FROM MAKSAJUMS WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int registretArStatusaNovertejumu(Maksajums maksajums,
            Rezervacija rezervacija) {
        int id = pievienot(maksajums);
        if (id > 0) {
            double apmaksats = new RezervacijaDAO()
                    .apmaksataSumma(maksajums.getRezervacijaId());
            if (apmaksats >= rezervacija.getKopsumma() - 0.001) {

                new RezervacijaDAO().mainitStatusu(
                        maksajums.getRezervacijaId(),
                        Rezervacija.STATUS_APMAKSATA);
            }
        }
        return id;
    }
}

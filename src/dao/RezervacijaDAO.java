package dao;

import db.DatabaseManager;
import model.Rezervacija;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RezervacijaDAO {

    public int pievienot(Rezervacija rezervacija) {
        String sql = "INSERT INTO REZERVACIJA (LIETOTAJS_ID, CELOJUMS_ID, "
                + "PERSONU_SKAITS, KOPSUMMA, STATUSS) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, rezervacija.getLietotajaId());
            ps.setInt(2, rezervacija.getCelojumaId());
            ps.setInt(3, rezervacija.getPersonuSkaits());
            ps.setDouble(4, rezervacija.getKopsumma());
            ps.setString(5, rezervacija.getStatuss() == null
                    ? Rezervacija.STATUS_AKTIVA : rezervacija.getStatuss());
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

    public List<Object[]> getTabula(int lietotajaId, String statuss) {
        List<Object[]> rindas = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT r.ID, l.VARDS || ' ' || l.UZVARDS AS KLIENTS, "
                + "c.NOSAUKUMS AS CELOJUMS, r.PERSONU_SKAITS, r.KOPSUMMA, "
                + "r.STATUSS, r.IZVEIDOTA "
                + "FROM REZERVACIJA r "
                + "JOIN LIETOTAJS l ON l.ID = r.LIETOTAJS_ID "
                + "JOIN CELOJUMS c ON c.ID = r.CELOJUMS_ID WHERE 1=1");
        List<Object> parametri = new ArrayList<>();
        if (lietotajaId > 0) {
            sql.append(" AND r.LIETOTAJS_ID = ?");
            parametri.add(lietotajaId);
        }
        if (statuss != null && !"VISAS".equals(statuss)) {
            sql.append(" AND r.STATUSS = ?");
            parametri.add(statuss);
        }
        sql.append(" ORDER BY r.ID DESC");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rindas.add(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("KLIENTS"),
                        rs.getString("CELOJUMS"),
                        rs.getInt("PERSONU_SKAITS"),
                        rs.getDouble("KOPSUMMA"),
                        rs.getString("STATUSS"),
                        rs.getTimestamp("IZVEIDOTA").toLocalDateTime()
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

    public Rezervacija pecId(int id) {
        String sql = "SELECT ID, LIETOTAJS_ID, CELOJUMS_ID, PERSONU_SKAITS, "
                + "KOPSUMMA, STATUSS, IZVEIDOTA FROM REZERVACIJA WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Rezervacija(
                            rs.getInt("ID"),
                            rs.getInt("LIETOTAJS_ID"),
                            rs.getInt("CELOJUMS_ID"),
                            rs.getInt("PERSONU_SKAITS"),
                            rs.getDouble("KOPSUMMA"),
                            rs.getString("STATUSS"),
                            rs.getTimestamp("IZVEIDOTA").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean piederLietotajam(int rezervacijaId, int lietotajaId) {
        String sql = "SELECT ID FROM REZERVACIJA WHERE ID = ? AND LIETOTAJS_ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rezervacijaId);
            ps.setInt(2, lietotajaId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mainitStatusu(int id, String statuss) {
        String sql = "UPDATE REZERVACIJA SET STATUSS = ? WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, statuss);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dzest(int id) {
        String sql = "DELETE FROM REZERVACIJA WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double apmaksataSumma(int rezervacijaId) {
        String sql = "SELECT COALESCE(SUM(SUMMA), 0) FROM MAKSAJUMS "
                + "WHERE REZERVACIJA_ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rezervacijaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> getVesture(int lietotajaId, String statuss) {
        List<Object[]> rindas = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT r.ID, c.NOSAUKUMS AS CELOJUMS, r.PERSONU_SKAITS, "
                + "r.KOPSUMMA, r.STATUSS, r.IZVEIDOTA, "
                + "COALESCE(m.APM, 0) AS APMAKSATS "
                + "FROM REZERVACIJA r "
                + "JOIN CELOJUMS c ON c.ID = r.CELOJUMS_ID "
                + "LEFT JOIN (SELECT REZERVACIJA_ID, SUM(SUMMA) AS APM "
                + "           FROM MAKSAJUMS GROUP BY REZERVACIJA_ID) m "
                + "     ON m.REZERVACIJA_ID = r.ID "
                + "WHERE 1=1");
        List<Object> parametri = new ArrayList<>();
        if (lietotajaId > 0) {
            sql.append(" AND r.LIETOTAJS_ID = ?");
            parametri.add(lietotajaId);
        }
        if (statuss != null && !"VISAS".equals(statuss)) {
            sql.append(" AND r.STATUSS = ?");
            parametri.add(statuss);
        }
        sql.append(" ORDER BY r.IZVEIDOTA DESC, r.ID DESC");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rindas.add(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("CELOJUMS"),
                        rs.getInt("PERSONU_SKAITS"),
                        rs.getDouble("KOPSUMMA"),
                        rs.getString("STATUSS"),
                        rs.getTimestamp("IZVEIDOTA").toLocalDateTime()
                                .format(java.time.format.DateTimeFormatter
                                        .ofPattern("dd.MM.yyyy HH:mm")),
                        rs.getDouble("APMAKSATS")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rindas;
    }

    public Timestamp izveidosanasLaiks(int id) {
        String sql = "SELECT IZVEIDOTA FROM REZERVACIJA WHERE ID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("IZVEIDOTA");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

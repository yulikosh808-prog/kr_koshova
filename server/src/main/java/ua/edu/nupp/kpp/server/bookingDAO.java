package ua.edu.nupp.kpp.server;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class bookingDAO {
    public void add(booking b) throws SQLException {
        String sql = "INSERT INTO `booking` (userId, timeId, status, countF1, hoursF2, extraEqF3, `index`, date) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            bind(p, b);
            p.executeUpdate();
        }
    }
    public List<booking> getAll() throws SQLException {
        List<booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking ORDER BY id";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(map(r));
            }
        }
        return list;
    }
    public booking getById(long id) throws SQLException {
        String sql = "SELECT * FROM booking WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }
    public void update(booking b) throws SQLException {
        String sql = "UPDATE booking SET userId=?, timeId=?, status=?, countF1=?, hoursF2=?, extraEqF3=?, `index`=?, date=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            bind(p, b);
            p.setLong(9, b.getId());
            p.executeUpdate();
        }
    }
    public void updateStatus(long id, bookingStatus status) throws SQLException {
        String sql = "UPDATE booking SET status=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, status.name());
            p.setLong(2, id);
            p.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM booking WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            p.executeUpdate();
        }
    }
    private void bind(PreparedStatement p, booking b) throws SQLException {
        p.setLong(1, b.getUserId());
        p.setLong(2, b.getTimeId());
        p.setString(3, b.getStatus().name());
        p.setInt(4, b.getCountF1());
        p.setDouble(5, b.getHoursF2());
        p.setInt(6, b.getExtraEqF3());
        p.setDouble(7, b.getIndex());
        p.setTimestamp(8, b.getDate() != null ? Timestamp.valueOf(b.getDate()) : null);
    }
    private booking map(ResultSet r) throws SQLException {
        Timestamp timestamp = r.getTimestamp("date");
        LocalDateTime localDateTime = (timestamp != null) ? timestamp.toLocalDateTime() : null;
        return new booking(
                r.getLong("id"),
                r.getLong("userId"),
                r.getLong("timeId"),
                bookingStatus.valueOf(r.getString("status")),
                r.getInt("countF1"),
                r.getDouble("hoursF2"),
                r.getInt("extraEqF3"),
                r.getDouble("index"),
                localDateTime
        );
    }
}

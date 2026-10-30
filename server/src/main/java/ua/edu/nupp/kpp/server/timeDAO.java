package ua.edu.nupp.kpp.server;

import com.mysql.cj.util.DnsSrv;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class timeDAO {
    public void add(time t) throws SQLException {
        String sql = "INSERT INTO `time` (sportObjectId, startTime, endTime, statusAvailable) VALUES(?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
         PreparedStatement p = c.prepareStatement(sql)) {
        bind(p, t);
        p.executeUpdate();
        }
    }
    public List<time> getAll() throws SQLException {
        List<time> list = new ArrayList<>();
        String sql = "SELECT * FROM `time` ORDER BY id";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(map(r));
            }
        }
        return list;
    }
    public time getById(long id) throws SQLException {
        String sql = "SELECT * FROM `time` WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }
    public void update(time t) throws SQLException {
        String sql = "UPDATE `time` SET sportObjectId=?, startTime=?, endTime=?, statusAvailable=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            bind(p, t);
            p.setLong(5, t.getId());
            p.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM `time` WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            p.executeUpdate();
        }
    }
    private void bind(PreparedStatement p, time t) throws SQLException {
        p.setLong(1, t.getSportObjectId());
        p.setTimestamp(2, t.getStartTime() != null ? Timestamp.valueOf(t.getStartTime()) : null);
        p.setTimestamp(3, t.getEndTime() != null ? Timestamp.valueOf(t.getEndTime()) : null);
        p.setBoolean(4, t.isStatusAvailable());
    }
    private time map(ResultSet r) throws SQLException {
        Timestamp startTimestamp = r.getTimestamp("startTime");
        LocalDateTime startLocalTime = (startTimestamp != null) ? startTimestamp.toLocalDateTime() : null;
        Timestamp endTimestamp = r.getTimestamp("endTime");
        LocalDateTime endLocalTime = (endTimestamp != null) ? endTimestamp.toLocalDateTime() : null;
        return new time(
                r.getLong("id"),
                r.getLong("sportObjectId"),
                startLocalTime,
                endLocalTime,
                r.getBoolean("statusAvailable")
        );
    }
}

package ua.edu.nupp.kpp.server;

import javax.xml.transform.sax.SAXResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sportObjectsDAO {
    public void add(sportObjects obj) throws SQLException {
        String sql = "INSERT INTO sport_objects(name, description, price) VALUES(?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            bind(p, obj);
            p.executeUpdate();
        }
    }
    public List<sportObjects> getAll() throws SQLException {
        List<sportObjects> list = new ArrayList<>();
        String sql = "SELECT * FROM sport_objects ORDER BY id";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(map(r));
            }
        }
        return list;
    }
    public sportObjects getById(long id) throws SQLException {
        String sql = "SELECT * FROM sport_objects WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }
    public void update(sportObjects obj) throws SQLException {
        String sql = "UPDATE sport_objects SET name=?, description=?, price=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            bind(p, obj);
            p.setLong(4, obj.getId());
            p.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM sport_objects WHERE id=?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            p.executeUpdate();
        }
    }
    private void bind(PreparedStatement p, sportObjects obj) throws SQLException {
        p.setString(1, obj.getName());
        p.setString(2, obj.getDescription());
        p.setDouble(3, obj.getPrice());
    }
    private sportObjects map(ResultSet r) throws SQLException {
        return new sportObjects(
                r.getLong("id"),
                r.getString("name"),
                r.getString("description"),
                r.getDouble("price")
        );
    }
}

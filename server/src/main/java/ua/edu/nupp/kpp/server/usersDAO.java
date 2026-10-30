package ua.edu.nupp.kpp.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class usersDAO {
    public void add(users user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, user.getUsername());
            p.setString(2, user.getEmail());
            p.setString(3, user.getPassword());
            p.setString(4, user.getRole());
            p.executeUpdate();
        }
    }
    public List<users> getAll() throws SQLException {
        List<users> list = new ArrayList<>();
        String sql = "SELECT id, username, email, password, role FROM users ORDER BY id";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery()) {
            while (r.next()) {
                list.add(new users(
                        r.getLong("id"),
                        r.getString("username"),
                        r.getString("email"),
                        r.getString("password"),
                        r.getString("role")
                ));
            }
        }
        return list;
    }
    public users getById(long id) throws SQLException {
        String sql = "SELECT id, username, email, password, role FROM users WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return new users(
                            r.getLong("id"),
                            r.getString("username"),
                            r.getString("email"),
                            r.getString("password"),
                            r.getString("role")
                    );
                }
            }
        }
        return null;
    }
    public void update(users user) throws SQLException {
        String sql = "UPDATE users SET username = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, user.getUsername());
            p.setString(2, user.getEmail());
            p.setString(3, user.getPassword());
            p.setString(4, user.getRole());
            p.setLong(5, user.getId());
            p.executeUpdate();
        }
    }
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
        PreparedStatement p = c.prepareStatement(sql)) {
            p.setLong(1, id);
            p.executeUpdate();
        }
    }
}

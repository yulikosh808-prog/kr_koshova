package ua.edu.nupp.kpp.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/sport_info";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    private DBUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            System.out.println("Підключення до MySQL успішне.");
        } catch (SQLException e) {
            System.out.println("Помилка підключення до MySQL:");
            System.out.println(e.getMessage());
        }
    }
}

package ua.edu.nupp.kpp.server;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class usersService {
    private static Logger LOG =Logger.getLogger(usersService.class.getName());
    private final usersDAO dao = new usersDAO();
    public List<users> getAll() throws SQLException {
        return dao.getAll();
    }
    public users getById(Long id) throws SQLException {
        return dao.getById(id);
    }
    public void add(users u) throws SQLException {
        validate(u);
        dao.add(u);
        LOG.info("Додано користувача");
    }
    public void update(users u) throws SQLException {
        validate(u);
        dao.update(u);
        LOG.info("Оновлено користувача");
    }
    public void delete(Long id) throws SQLException {
        dao.delete(id);
        LOG.info("Видалено користувача");
    }
    static void validate(users u) {
        if (u == null || blank(u.getUsername()) || blank(u.getEmail()) || blank(u.getPassword())) {
            throw new IllegalArgumentException("Усі поля користувача повинні обов'язково бути заповнені");
        }
    }
    private static boolean blank(String v) {
        return v == null || v.isBlank();
    }
}

package ua.edu.nupp.kpp.server;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class sportObjectsService {
    private static final Logger LOG = Logger.getLogger(sportObjectsService.class.getName());
    private final sportObjectsDAO dao = new sportObjectsDAO();
    public List<sportObjects> getAll() throws SQLException {
        return dao.getAll();
    }
    public sportObjects getById(Long id) throws SQLException {
        return dao.getById(id);
    }
    public void add(sportObjects s) throws SQLException {
        validate(s);
        dao.add(s);
        LOG.info("Додано спортивний об'єкт");
    }
    public void update(sportObjects s) throws SQLException {
        validate(s);
        dao.update(s);
        LOG.info("Оновлено спортивний об'єкт");
    }
    public void delete(Long id) throws SQLException {
        dao.delete(id);
        LOG.info("Видалено спортивний об'єкт");
    }
    static void validate(sportObjects s) {
        if (s == null || blank(s.getName()) || s.getPrice() < 0) {
            throw new IllegalArgumentException("Некоректні дані спортивного об'єкта");
        }
    }
    private static boolean blank(String v) {
        return v == null || v.isBlank();
    }
}

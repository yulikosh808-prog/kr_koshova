package ua.edu.nupp.kpp.server;

import java.awt.desktop.AppForegroundListener;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.List;

public class timeService {
    private static final Logger LOG = Logger.getLogger(timeService.class.getName());
    private final timeDAO dao = new timeDAO();
    private final sportObjectsDAO sportObjectsDAO = new sportObjectsDAO();
    public List<time> getAll() throws SQLException {
        return dao.getAll();
    }
    public time getById(Long id) throws SQLException {
        return dao.getById(id);
    }
    public void add(time t) throws SQLException {
        validate(t);
        requireSportObject(t.getSportObjectId());
        dao.add(t);
        LOG.info("Додано час");
    }
    public void update(time t) throws SQLException {
        validate(t);
        requireSportObject(t.getSportObjectId());
        dao.update(t);
        LOG.info("Оновлено час");
    }
    public void delete(Long id) throws SQLException {
        dao.delete(id);
        LOG.info("Видалено час");
    }
    static void validate(time t) {
        if (t == null || t.getSportObjectId() <= 0) {
            throw new IllegalArgumentException("Некоректні дані часу");
        }
    }
    private void requireSportObject(Long sportObjectId) throws SQLException {
        if (sportObjectsDAO.getById(sportObjectId) == null) {
            throw new IllegalArgumentException("Спортивний об'єкт не знайдено");
        }
    }
}

package ua.edu.nupp.kpp.server;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class bookingService {
    private static final Logger LOG = Logger.getLogger(bookingService.class.getName());
    private final bookingDAO dao = new bookingDAO();
    private final usersDAO usersDAO = new usersDAO();
    private final timeDAO timeDAO = new timeDAO();
    public List<booking> getAll() throws SQLException {
        return dao.getAll();
    }
    public booking getById(Long id) throws SQLException {
        return dao.getById(id);
    }
    public void add(Long userId, Long timeId, int countF1, double hoursF2, int extraEqF3, java.time.LocalDateTime date) throws SQLException {
        if (hoursF2 < 0.0 || hoursF2 > 100.0 || extraEqF3 < 0 || extraEqF3 > 100.0) {
            throw new IllegalArgumentException("F3 i F3 мають бути в діапазоні від 0 до 100");
        }
        requireUser(userId);
        requireTime(timeId);
        booking b = fromBookingData(null, userId, timeId, countF1, hoursF2, extraEqF3, bookingStatus.CREATED, date);
        dao.add(b);
        LOG.info("Створено бронювання");
    }
    public void update(Long id, Long userId, Long timeId, int countF1, double hoursF2, int extraEqF3, java.time.LocalDateTime date) throws SQLException {
        booking current = dao.getById(id);
        if (current == null) {
            throw new IllegalArgumentException("Бронювання не знайдено");
        }
        requireUser(userId);
        requireTime(timeId);
        booking b = fromBookingData(id, userId, timeId, countF1, hoursF2, extraEqF3, current.getStatus(), date);
        dao.update(b);
        LOG.info("Оновлено бронювання");
    }
    public static boolean isAllowedTransition(bookingStatus from, bookingStatus to) {
        return (from == bookingStatus.CREATED && to == bookingStatus.CONFIRMED)
                || (from == bookingStatus.CONFIRMED && to == bookingStatus.ACTIVE)
                || (from == bookingStatus.ACTIVE && to == bookingStatus.COMPLETED);
    }
    public void changeState(Long id, bookingStatus nextStatus) throws SQLException {
        booking current = dao.getById(id);
        if (current == null) {
            throw new IllegalArgumentException("Бронювання не знайдено");
        }
        if (!isAllowedTransition(current.getStatus(), nextStatus)) {
            LOG.warning("Недопустимий перехід стану: " + current.getStatus() + " -> " + nextStatus);
            throw new IllegalArgumentException("Недопустимий перехід стану");
        }
        dao.updateStatus(id, nextStatus);
        LOG.info("Змінено статус бронювання");
    }
    public void delete(Long id) throws SQLException {
        dao.delete(id);
        LOG.info("Видалено бронювання");
    }
    private booking fromBookingData(Long id, Long userId, Long timeId, int countF1,double hoursF2, double extraEqF3, bookingStatus status, java.time.LocalDateTime date) {
        double f1 = RiskCalculator.factor1(countF1, (int)50.0);
        double f2 = Math.max(0.0, Math.min(100.0, hoursF2));
        double f3 = Math.max(0.0, Math.min(100.0, (double) extraEqF3 * 20.0));
        double index = RiskCalculator.calculate(f1, f2, f3);
        return  new booking(id, userId, timeId, status, countF1, f2, (int)f3, index, date);
    }
    private void requireUser(Long userId) throws SQLException {
        if (usersDAO.getById(userId) == null) {
            throw new IllegalArgumentException("Користувача не знайдено");
        }
    }
    private void requireTime(Long timeId) throws SQLException {
        if (timeDAO.getById(timeId) == null) {
            throw new IllegalArgumentException("Час не знайдено");
        }
    }
}

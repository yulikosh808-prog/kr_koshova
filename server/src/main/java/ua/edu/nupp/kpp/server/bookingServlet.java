package ua.edu.nupp.kpp.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class bookingServlet extends HttpServlet {
    private final bookingService service = new bookingService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String action = req.getParameter("action");
            if ("delete".equals(action)) {
                service.delete(id(req));
            }
            writeJson(resp, service.getAll());
        } catch (Exception e) {
            writeError(resp, e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            String action = req.getParameter("action");
            if ("delete".equals(action)) {
                service.delete(id(req));
            } else if ("transition".equals(action)) {
                service.changeState(id(req), bookingStatus.valueOf(req.getParameter("status")));
            } else {
                booking value = new booking(
                        "update".equals(action) ? id(req) : null,
                        Long.parseLong(req.getParameter("userId")),
                        Long.parseLong(req.getParameter("timeId")),
                        bookingStatus.valueOf(req.getParameter("status")),
                        Integer.parseInt(req.getParameter("countF1")),
                        Double.parseDouble(req.getParameter("hoursF2")),
                        Integer.parseInt(req.getParameter("extraEqF3")),
                        Double.parseDouble(req.getParameter("index")),
                        req.getParameter("date") != null && !req.getParameter("date").isEmpty()
                            ? LocalDate.parse(req.getParameter("date"))
                            : null
                );
                if ("add".equals(action)) {
                    service.add(
                            Long.parseLong(req.getParameter("userId")),
                            Long.parseLong(req.getParameter("timeId")),
                            Integer.parseInt(req.getParameter("countF1")),
                            Double.parseDouble(req.getParameter("hoursF2")),
                            Integer.parseInt(req.getParameter("extraEqF3")),
                            req.getParameter("date") != null && !req.getParameter("date").isEmpty()
                                    ? LocalDate.parse(req.getParameter("date"))
                                    : null
                    );
                } else if ("update".equals(action)) {
                    service.update(
                            id(req),
                            Long.parseLong(req.getParameter("userId")),
                            Long.parseLong(req.getParameter("timeId")),
                            Integer.parseInt(req.getParameter("countF1")),
                            Double.parseDouble(req.getParameter("hoursF2")),
                            Integer.parseInt(req.getParameter("extraEqF3")),
                            req.getParameter("date") != null && !req.getParameter("date").isEmpty()
                                    ? LocalDate.parse(req.getParameter("date"))
                                    : null
                    );
                } else {
                    throw new IllegalArgumentException("Невідома операція");
                }
            }
            writeJson(resp, service.getAll());
        } catch (Exception e) {
            writeError(resp, e);
        }
    }
    private void writeJson(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        mapper.writeValue(resp.getWriter(), data);
    }
    private void writeError(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writeJson(resp, Map.of("error", e.getMessage() == null ? "Помилка" : e.getMessage()));
    }
    private long id(HttpServletRequest req) {
        return Long.parseLong(req.getParameter("id"));
    }
}

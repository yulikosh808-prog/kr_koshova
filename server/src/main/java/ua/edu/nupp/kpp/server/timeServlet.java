package ua.edu.nupp.kpp.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class timeServlet extends HttpServlet {
    private final timeService service = new timeService();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
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
            } else {
                Long timeId = "update".equals(action) ? id(req) : null;
                Long sportObjectId = Long.parseLong(req.getParameter("sportObjectId"));
                LocalDateTime startTime = req.getParameter("startTime") != null ? LocalDateTime.parse(req.getParameter("startTime")) : null;
                LocalDateTime endTime = req.getParameter("endTime") != null ? LocalDateTime.parse(req.getParameter("endTime")) : null;
                boolean statusAvailable = req.getParameter("statusAvailable") != null && Boolean.parseBoolean(req.getParameter("statusAvailable"));
                time value = new time(timeId, sportObjectId, startTime, endTime, statusAvailable);
                if ("add".equals(action)) {
                    service.add(value);
                } else if ("update".equals(action)) {
                    service.update(value);
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
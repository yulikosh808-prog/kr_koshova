package ua.edu.nupp.kpp.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class HttpSportInfoClient {
    private static final String BASE = "http://localhost:8080/sport-info-server/";
    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public HttpSportInfoClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public List<users> getUsers() {
        return Arrays.asList(read(get("users"), users[].class));
    }
    public void addUser(users u) {
        post("users", "action=add&username=" + enc(u.getUsername()) +
                "&email=" + enc(u.getEmail()) +
                "&password=" + enc(u.getPassword()) +
                "&role=" + enc(u.getRole()));
    }
    public void updateUser(users u) {
        post("users", "action=update&id=" + u.getId() +
                "&username=" + enc(u.getUsername()) +
                "&email=" + enc(u.getEmail()) +
                "&password=" + enc(u.getPassword()) +
                "&role=" + enc(u.getRole()));
    }
    public void deleteUser(long id) {
        post("users", "action=delete&id=" + id);
    }
    public List<sportObjects> getSportObjects() {
        return Arrays.asList(read(get("sportObjects"), sportObjects[].class));
    }
    public void addSportObjects(sportObjects obj) {
        post("sportObjects", "action=add&name=" + enc(obj.getName()) +
                "&description=" + enc(obj.getDescription()) +
                "&price=" + obj.getPrice());
    }
    public void updateSportObjects(sportObjects obj) {
        post("sportObjects", "action=update&id=" + obj.getId() +
                "&name=" + enc(obj.getName()) +
                "&description=" + enc(obj.getDescription()) +
                "&price=" + obj.getPrice());
    }
    public void deleteSportObjects(long id) {
        post("sportObjects", "action=delete&id=" + id);
    }
    public List<time> getTime() {
        return Arrays.asList(read(get("time"), time[].class));
    }
    public void addTime(time t) {
        post("time", "action=add&sportObjectId=" + t.getSportObjectId() +
                "&startTime=" + enc(t.getStartTime()) +
                "&endTime=" + enc(t.getEndTime()) +
                "&statusAvailable=" + t.isStatusAvailable());
    }
    public void updateTime(time t) {
        post("time", "action=update&id=" + t.getId() +
                "&sportObjectId=" + t.getSportObjectId() +
                "&startTime=" + enc(t.getStartTime()) +
                "&endTime=" + enc(t.getEndTime()) +
                "&statusAvailable=" + t.isStatusAvailable());
    }
    public void deleteTime(long id) {
        post("time", "action=delete&id=" + id);
    }
    public List<booking> getBooking() {
        return Arrays.asList(read(get("booking"), booking[].class));
    }
    public void addBooking(booking b) {
        post("booking", bookingForm("add", b));
    }
    public void updateBooking(booking b) {
        post("booking", bookingForm("update", b) + "&id=" + b.getId());
    }
    public void deleteBooking(long id) {
        post("booking", "action=delete&id=" + id);
    }
    public void changeBookingStatus(long id, bookingStatus status) {
        post("booking", "action=transition&id=" + id + "&status=" + enc(status != null ? status.name() : ""));
    }
    private String bookingForm(String action, booking b) {
        return "action=" + action +
                "&userId=" + b.getUserId() +
                "&timeId=" + b.getTimeId() +
                "&status=" + (b.getStatus() != null ? enc(b.getStatus().name()) : "") +
                "&countF1=" + b.getCountF1() +
                "&hoursF2=" + b.getHoursF2() +
                "&extraEqF3=" + b.getExtraEqF3() +
                "&index=" + b.getIndex() +
                "&date=" + enc(b.getDate() != null ? b.getDate().toString() : "");

    }
    private String get(String path) {
        HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + path)).GET().build();
        return send(req);
    }
    private String post(String path, String form) {
        HttpRequest req = HttpRequest.newBuilder(URI.create(BASE + path))
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        return send(req);
    }
    private String send(HttpRequest req) {
        try {
            HttpResponse<String> r = httpClient.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (r.statusCode() >= 400) {
                throw new RuntimeException(r.body());
            }
            return r.body();
        } catch (Exception e) {
            throw new RuntimeException("Помилка HTTP: " + e.getMessage(), e);
        }
    }
    private <T> T read(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Помилка JSON" + json, e);
        }
    }
    private String enc(String s) {
        return URLEncoder.encode(s == null ? "" : s, StandardCharsets.UTF_8);
    }
}

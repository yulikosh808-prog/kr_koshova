package org.example;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.util.Locale;

public class Time {
    private int id;
    private int sport_object_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private boolean status_available;

    public Time() {}
    public Time(int id, int sport_object_id, LocalDateTime start_time, LocalDateTime end_time, boolean status_available) {
        this.id = id;
        this.sport_object_id = sport_object_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status_available = status_available;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSport_object_id() {
        return sport_object_id;
    }
    public void setSport_object_id(int sport_object_id) {
        this.sport_object_id = sport_object_id;
    }
    public LocalDateTime getStart_time() {
        return start_time;
    }
    public void setStart_time(LocalDateTime startTime) {
        this.start_time = start_time;
    }
    public LocalDateTime getEnd_time() {
        return end_time;
    }
    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }
    public boolean status_available() {
        return status_available;
    }
    public void setStatus_available(boolean status_available) {
        this.status_available = status_available;
    }
}

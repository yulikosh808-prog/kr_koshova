package ua.edu.nupp.kpp.server;

import java.time.LocalDateTime;

public class time {
    private Long id;
    private long sportObjectId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean statusAvailable;

    public time() {
    }
    public time(Long id, long sportObjectId, LocalDateTime startTime, LocalDateTime endTime, boolean statusAvailable) {
        this.id = id;
        this.sportObjectId = sportObjectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusAvailable = statusAvailable;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getSportObjectId() {
        return sportObjectId;
    }
    public void setSportObjectId(long sportObjectId) {
        this.sportObjectId = sportObjectId;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public boolean isStatusAvailable() {
        return statusAvailable;
    }
    public void setStatusAvailable(boolean statusAvailable) {
        this.statusAvailable = statusAvailable;
    }
}

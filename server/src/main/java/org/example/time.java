package org.example;

import java.time.LocalDateTime;

public class time {
    private int id;
    private int sportObjectId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean statusAvailable;

    public time() {}
    public time(int id, int sportObjectId, LocalDateTime startTime, LocalDateTime endTime, boolean statusAvailable) {
        this.id = id;
        this.sportObjectId = sportObjectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusAvailable = statusAvailable;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSportObjectId() {
        return sportObjectId;
    }
    public void setSportObjectId(int sportObjectId) {
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
    public boolean statusAvailable() {
        return statusAvailable;
    }
    public void setStatusAvailable(boolean statusAvailable) {
        this.statusAvailable = statusAvailable;
    }
}

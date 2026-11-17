package ua.edu.nupp.kpp.client;

public class time {
    private Long id;
    private long sportObjectId;
    private String startTime;
    private String endTime;
    private boolean statusAvailable;

    public time() {
    }
    public time(Long id, long sportObjectId, String startTime, String endTime, boolean statusAvailable) {
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
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public boolean isStatusAvailable() {
        return statusAvailable;
    }
    public void setStatusAvailable(boolean statusAvailable) {
        this.statusAvailable = statusAvailable;
    }
}


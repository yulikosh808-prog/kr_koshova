package ua.edu.nupp.kpp.server;

import java.time.LocalDateTime;

public class booking {
    private Long id;
    private long userId;
    private long timeId;
    private bookingStatus status;
    private int countF1;
    private double hoursF2;
    private int extraEqF3;
    private double index;
    private LocalDateTime date;

    public booking() {
    }
    public booking(Long id, long userId, long timeId, bookingStatus status, int countF1, double hoursF2, int extraEqF3, double index, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.timeId = timeId;
        this.status = status;
        this.countF1 = countF1;
        this.hoursF2 = hoursF2;
        this.extraEqF3 = extraEqF3;
        this.index = index;
        this.date = date;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getTimeId() {
        return timeId;
    }
    public void setTimeId(long timeId) {
        this.timeId = timeId;
    }
    public bookingStatus getStatus() {
        return status;
    }
    public void setStatus(bookingStatus status) {
        this.status = status;
    }
    public int getCountF1() {
        return countF1;
    }
    public void setCountF1(int countF1) {
        this.countF1 = countF1;
    }
    public double getHoursF2() {
        return hoursF2;
    }
    public void setHoursF2(double hoursF2) {
        this.hoursF2 = hoursF2;
    }
    public int getExtraEqF3() {
        return extraEqF3;
    }
    public void setExtraEqF3(int extraEqF3) {
        this.extraEqF3 = extraEqF3;
    }
    public double getIndex() {
        return index;
    }
    public void setIndex(double index) {
        this.index = index;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

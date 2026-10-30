package org.example;

import java.time.LocalDateTime;

public class booking {
    private int id;
    private int userId;
    private int timeId;
    private String status; // СТВОРЕНО, ПІДТВЕРДЖЕНО, АКТИВНЕ, ЗАВЕРШЕНО
    private int countF1;
    private double hoursF2;
    private int extraEqF3;
    private double index;
    private LocalDateTime date;

    public booking() {}
    public booking(int id, int userId, int timeId, String status, int countF1, double hoursF2, int extraEqF3, double index, LocalDateTime date) {
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
    public void calculate_index() {
        this.index = 0.4 * this.countF1 + this.hoursF2 + 0.25 * this.extraEqF3;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getTimeId() {
        return timeId;
    }
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
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

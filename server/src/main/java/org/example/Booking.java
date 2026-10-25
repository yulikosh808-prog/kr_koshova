package org.example;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int user_id;
    private int time_id;
    private String status; // СТВОРЕНО, ПІДТВЕРДЖЕНО, АКТИВНЕ, ЗАВЕРШЕНО
    private int count_f1;
    private double hours_f2;
    private int extra_eq_f3;
    private double index;
    private LocalDateTime date;

    public Booking() {}
    public Booking(int id, int user_id, int time_id, String status, int count_f1, double hours_f2, int extra_eq_f3, double index, LocalDateTime date) {
        this.id = id;
        this.user_id = user_id;
        this.time_id = time_id;
        this.status = status;
        this.count_f1 = count_f1;
        this.hours_f2 = hours_f2;
        this.extra_eq_f3 = extra_eq_f3;
        this.index = index;
        this.date = date;
    }
    public void calculate_index() {
        this.index = 0.4 * this.count_f1 + this.hours_f2 + 0.25 * this.extra_eq_f3;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int userId) {
        this.user_id = user_id;
    }
    public int getTime_id() {
        return time_id;
    }
    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getCount_f1() {
        return count_f1;
    }
    public void setCount_f1(int count_f1) {
        this.count_f1 = count_f1;
    }
    public double getHours_f2() {
        return hours_f2;
    }
    public void setHours_f2(double hours_f2) {
        this.hours_f2 = hours_f2;
    }
    public int getExtra_eq_f3() {
        return extra_eq_f3;
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

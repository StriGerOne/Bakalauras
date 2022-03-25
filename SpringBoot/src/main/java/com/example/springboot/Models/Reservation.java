package com.example.springboot.Models;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    @Id
    private Long id;
    private String reservation_name;
    private String reservation_lname;
    private LocalDateTime reservation_time;
    private String reservation_restouran;
    private  LocalDate today_date;

    public Reservation (Long id, String reservation_name, String reservation_lname, LocalDateTime reservation_time, String reservation_restouran, LocalDate today_date) {
        this.id = id;
        this.reservation_name = reservation_name;
        this.reservation_lname = reservation_lname;
        this.reservation_time = reservation_time;
        this.reservation_restouran = reservation_restouran;
        this.today_date = today_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservation_name() {
        return reservation_name;
    }

    public void setReservation_name(String reservation_name) {
        this.reservation_name = reservation_name;
    }

    public String getReservation_lname() {
        return reservation_lname;
    }

    public void setReservation_lname(String reservation_lname) {
        this.reservation_lname = reservation_lname;
    }

    public LocalDateTime getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(LocalDateTime reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getReservation_restouran() {
        return reservation_restouran;
    }

    public void setReservation_restouran(String reservation_restouran) {
        this.reservation_restouran = reservation_restouran;
    }

    public LocalDate getToday_date() {
        return today_date;
    }

    public void setToday_date(LocalDate today_date) {
        this.today_date = today_date;
    }
}

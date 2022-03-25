package com.example.springboot.Models;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    @Id
    private String id;
    private String reservation_name;
    private String reservation_lname;
    private LocalDate date;
    private LocalTime time;
    private String reservation_restouran;

    public Reservation (String id, String reservation_name, String reservation_lname, LocalDate date, LocalTime time, String reservation_restouran) {
        this.id = id;
        this.reservation_name = reservation_name;
        this.reservation_lname = reservation_lname;
        this.date = date;
        this.time = time;
        this.reservation_restouran = reservation_restouran;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getReservation_restouran() {
        return reservation_restouran;
    }

    public void setReservation_restouran(String reservation_restouran) {
        this.reservation_restouran = reservation_restouran;
    }
}

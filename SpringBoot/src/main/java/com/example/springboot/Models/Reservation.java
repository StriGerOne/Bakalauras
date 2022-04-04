package com.example.springboot.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Reservation {

    @Id
    private Long id;
    private String people_amount;
    private String name;
    private String lastname;
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservation_time;
    private String reservation_restouran;

    public Reservation (Long id, String people_amount, String name, String lastname, LocalDateTime reservation_time, String reservation_restouran) {
        this.id = id;
        this.people_amount = people_amount;
        this.name = name;
        this.lastname = lastname;
        this.reservation_time = reservation_time;
        this.reservation_restouran = reservation_restouran;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeople_amount() {
        return people_amount;
    }

    public void setPeople_amount(String people_amount) {
        this.people_amount = people_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String reservation_name) {
        this.name = reservation_name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String reservation_lname) {
        this.lastname = reservation_lname;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public LocalDateTime getReservation_time() {
        return reservation_time;
    }
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    public void setReservation_time(LocalDateTime reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getReservation_restouran() {
        return reservation_restouran;
    }

    public void setReservation_restouran(String reservation_restouran) {
        this.reservation_restouran = reservation_restouran;
    }
}

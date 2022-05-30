package com.example.reservation.Models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private Long userId;
    private LocalDateTime today;
    private int peopleAmount;
    private LocalDateTime reservationTime;
    private LocalTime duration;
    private String status;


    public Reservation(Long id, Long restaurantId, String restaurantName, Long userId, int peopleAmount, LocalDateTime reservationTime, LocalTime duration) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.peopleAmount = peopleAmount;
        this.reservationTime = reservationTime;
        this.duration = duration;
        this.status = "Patvirtinta";
    }

    public Reservation(Long id, Long restaurantId, String restaurantName, Long userId, LocalDateTime today, int peopleAmount, LocalDateTime reservationTime, LocalTime duration, String status) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.today = today;
        this.peopleAmount = peopleAmount;
        this.reservationTime = reservationTime;
        this.duration = duration;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restName) {
        this.restaurantName = restName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getToday() {
        return today;
    }

    public void setToday(LocalDateTime today) {
        this.today = today;
    }

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = "Patvirtinta";
    }

    @Override
    public String toString() {

        return "Jūsų rezervacijos:" + "\n" +
                "Restoranas: " + restaurantName + "\n" +
                "Rezervacijos data: " + today + "\n" +
                "Žmonių skaičius: " + peopleAmount + "\n"+
                "Nuo: " + reservationTime + "\n"+
                "Iki: " + duration + "\n"+
                "Statusas: '" + status + '\'';
    }
}

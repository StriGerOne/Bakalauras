package com.example.springboot.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {


    @Id
    private Long id;
    private Long restaurantId;
    private  String restaurantName;
    private Long userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime today = LocalDateTime.now();
    private int peopleAmount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationTime;
    @JsonFormat(pattern="HH:mm")
    private LocalTime duration;
    private String status;
    private String selectedSeat;

    public Reservation (Long id, Long restaurantId, String restaurantName, Long userId, int peopleAmount, LocalDateTime reservationTime, LocalTime duration, String selectedSeat) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.peopleAmount = peopleAmount;
        this.reservationTime = reservationTime;
        this.duration = duration;
        this.status = "Patvirtinta";
        this.selectedSeat = selectedSeat;
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
        this.restaurantName = restaurantName;
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

    public String setStatus(String status) {
        this.status = "Patvirtinta";
        return status;
    }

    public String getSelectedSeat() {
        return selectedSeat;
    }

    public void setSelectedSeat(String selectedSeat) {
        this.selectedSeat = selectedSeat;
    }
}

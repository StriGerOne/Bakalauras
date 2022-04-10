package com.example.springboot.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation {

    @Id
    private Long id;
    private Long restouranId;
    private Long userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime today = LocalDateTime.now();
    private int peopleAmount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationTime;
    @JsonFormat(pattern="HH:mm")
    private LocalTime duration;
    private int freeSpace;



    public Reservation (Long id, Long restouranId, Long userId, int peopleAmount,LocalDateTime reservationTime,LocalTime duration, int  freeSpace) {
        this.id = id;
        this.restouranId = restouranId;
        this.userId = userId;
        this.today = today;
        this.peopleAmount = peopleAmount;
        this.reservationTime = reservationTime;
        this.duration = duration;
        this.freeSpace = freeSpace;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestouranId() {
        return restouranId;
    }

    public void setRestouranId(Long restouranId) {
        this.restouranId = restouranId;
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

    public int getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }
}

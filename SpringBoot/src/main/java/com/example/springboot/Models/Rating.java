package com.example.springboot.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Rating {
    private Long id;
    private Long restaurantId;
    private Long userId;
    private float rating;
    private String comment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rateTime = LocalDateTime.now();

    public Rating(Long id, Long restaurantId, Long userId, float rating, String comment) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getRateTime() {
        return rateTime;
    }

    public void setRateTime(LocalDateTime rateTime) {
        this.rateTime = rateTime;
    }

    public void setComment(String comment) {
        this.comment = comment;

    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", userId=" + userId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
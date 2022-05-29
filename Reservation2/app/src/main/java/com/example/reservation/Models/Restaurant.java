package com.example.reservation.Models;

public class Restaurant {


    private Long id;
    private String restaurantName;
    private String summary;
    private String address;
    private String phone;
    private String email;
    private String jobTime;
    private String category;
    private int numberOfSeats;
    private String imageUrl;


    public Restaurant(Long id, String restaurantName, String summary, String address, String phone, String email, String jobTime, String category, int numberOfSeats) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.summary = summary;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.jobTime = jobTime;
        this.category = category;
        this.numberOfSeats = numberOfSeats;
    }

    public Restaurant() {
    }

    public Restaurant(Long id, String restaurantName, String summary, String address, String phone, String email, String jobTime, String category, String imageUrl, int numberOfSeats) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.summary = summary;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.jobTime = jobTime;
        this.category = category;
        this.imageUrl = imageUrl;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        restaurantName = restaurantName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        jobTime = jobTime;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        category = category;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

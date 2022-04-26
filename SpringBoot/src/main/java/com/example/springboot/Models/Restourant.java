package com.example.springboot.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Restourant {

    @Id
    @Field(name = "id")
    private Long id;
    private String restourantName;
    private String summary;
    private String address;
    private String phone;
    private String email;
    private String jobTime;
    private String category;
    private int numberOfSeats;

    public Restourant(Long id, String restourantName, String summary, String address, String phone, String email, String jobTime, String category, int numberOfSeats) {
        this.id = id;
        this.restourantName = restourantName;
        this.summary = summary;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.jobTime = jobTime;
        this.category = category;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestourantName() {
        return restourantName;
    }

    public void setRestourantName(String restourantName) {
        restourantName = restourantName;
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
}

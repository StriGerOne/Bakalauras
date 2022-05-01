package com.example.springboot.Models;

public class Tables {

    private Long restaurantId;
    private int seatAmount;
    private Long tableId;
    private int tableSeats;

    public Tables(Long restaurantId, int seatAmount, Long tableId, int tableSeats) {
        this.restaurantId = restaurantId;
        this.seatAmount = seatAmount;
        this.tableId = tableId;
        this.tableSeats = tableSeats;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getSeatAmount() {
        return seatAmount;
    }

    public void setSeatAmount(int seatAmount) {
        this.seatAmount = seatAmount;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public int getTableSeats() {
        return tableSeats;
    }

    public void setTableSeats(int tableSeats) {
        this.tableSeats = tableSeats;
    }
}



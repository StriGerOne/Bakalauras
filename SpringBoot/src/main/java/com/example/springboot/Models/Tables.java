package com.example.springboot.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Tables {
    @Id
    @Field(name = "tableId")
    private String tableId;
    private Long restaurantId;
    private int seatAmount;
    public Tables(String tableId, Long restaurantId, int seatAmount) {
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.seatAmount = seatAmount;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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



}



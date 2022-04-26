package com.example.springboot.Models;

public class Tables {

    private Long restouranId;
    private int alltableAmount;
    private int twoseat;
    private int fourseat;
    private int eightseat;

    public Tables(Long restouranId, int alltableAmount, int twoseat, int fourseat, int eightseat) {
        this.restouranId = restouranId;
        this.alltableAmount = alltableAmount;
        this.twoseat = twoseat;
        this.fourseat = fourseat;
        this.eightseat = eightseat;
    }

    public Long getRestouranId() {
        return restouranId;
    }

    public void setRestouranId(Long restouranId) {
        this.restouranId = restouranId;
    }

    public int getAlltableAmount() {
        return alltableAmount;
    }

    public void setAlltableAmount(int alltableAmount) {
        this.alltableAmount = alltableAmount;
    }

    public int getTwoseat() {
        return twoseat;
    }

    public void setTwoseat(int twoseat) {
        this.twoseat = twoseat;
    }

    public int getFourseat() {
        return fourseat;
    }

    public void setFourseat(int fourseat) {
        this.fourseat = fourseat;
    }

    public int getEightseat() {
        return eightseat;
    }

    public void setEightseat(int eightseat) {
        this.eightseat = eightseat;
    }
}



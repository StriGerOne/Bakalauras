package com.example.springboot.Models;

public class Tables {

    private Long restouranId;
    private int seatamount;
    private Long tableid;
    private int tableseats;

    public Tables(Long restouranId, int seatamount, Long tableid, int tableseats) {
        this.restouranId = restouranId;
        this.seatamount = seatamount;
        this.tableid = tableid;
        this.tableseats = tableseats;
    }

    public Long getRestouranId() {
        return restouranId;
    }

    public void setRestouranId(Long restouranId) {
        this.restouranId = restouranId;
    }

    public int getSeatamount() {
        return seatamount;
    }

    public void setSeatamount(int seatamount) {
        this.seatamount = seatamount;
    }

    public Long getTableid() {
        return tableid;
    }

    public void setTableid(Long tableid) {
        this.tableid = tableid;
    }

    public int getTableseats() {
        return tableseats;
    }

    public void setTableseats(int tableseats) {
        this.tableseats = tableseats;
    }
}



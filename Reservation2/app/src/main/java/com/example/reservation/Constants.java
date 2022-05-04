package com.example.reservation;

public class Constants {
     public static final String ADRESS = "http://192.168.8.101:8080/";          //Ukm IP:192.168.1.187  VLN IP:192.168.8.101
     public static final String USERAUTH = ADRESS + "authenticate";
     public static final String REGISTER = ADRESS + "addUser";
     public static final String RESTLIST = ADRESS + "allRestaurant";
     public static final String RESERVATE = ADRESS + "newReservation";
     public static final String RESERVATIONLIST = ADRESS + "allReservations";

     public static final String APPROVEDRESERVLIST = ADRESS + "reservationByUserIDAAndStatus";
     public static final String EDITUSER = ADRESS + "updateuser/{id}";



}

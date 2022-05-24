package com.example.reservation;

public class Constants {
     public static final String ADRESS = "http://192.168.1.125:8080/";          //Ukm IP:192.168.1.187  VLN IP:192.168.1.125
     public static final String SHAREDPREFERENCES = "sharedpreferences";
     public static final String USERAUTH = ADRESS + "authenticate";
     public static final String REGISTER = ADRESS + "addUser";
     public static final String RESTLIST = ADRESS + "allRestaurant";
     public static final String RESERVATE = ADRESS + "newReservation";
     public static final String NEWRATE = ADRESS + "rateRestaurant";
     public static String getReservationsByUser(Long id){
          return ADRESS + "getReservationsByUser" + "?UserId=" + id;
     }
     public static String getRateByUser(Long id) {
          return ADRESS + "getRateByUser" + "?UserId=" + id;
     }
     public static String updateUserInfo(Long id) {
          return ADRESS + "updateuser" + "?UserId=" + id;
     }
     public static String getRateByRestaurant(Long id){
          return ADRESS + "getRateByRestaurant" + "?RestaurantId=" + id;
     }
     public static final String TABLES = ADRESS + "checkReservationAvailabilityTables";







}

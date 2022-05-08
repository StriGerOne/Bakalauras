package com.example.reservation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.reservation.Constants;


public class SharedPreferenceProvider {

    private static volatile SharedPreferenceProvider INSTANCE;
    private SharedPreferences sharedPreferences;

    public static SharedPreferenceProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (SharedPreferenceProvider.class) {
                throw new RuntimeException("You must initialize SharedPreferenceProvider.class on Application level first!");
            }
        }
        return INSTANCE;
    }

    public static void init(Context context) {
        if (INSTANCE == null) {
            synchronized (SharedPreferenceProvider.class) {
                INSTANCE = new SharedPreferenceProvider(context);
            }
        }
    }

    public SharedPreferenceProvider(Context context){
        this.sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES,Context.MODE_PRIVATE);
    }

    public String getUserId() {
        return sharedPreferences.getString("UserId",null);
    }
    public String getUserName() {
        return sharedPreferences.getString("UserName","No info");
    }
    public String getUserSurname() {
        return sharedPreferences.getString("UserSurname","No info");
    }
    public String getUserUsername() {
        return sharedPreferences.getString("UserUsername","No info");
    }
    public String getUserPassword() {
        return sharedPreferences.getString("UserPassword","No info");
    }
    public String getUserPhone() {
        return sharedPreferences.getString("UserPhone","No info");
    }
    public String getUserEmail() {
        return sharedPreferences.getString("UserEmail","No info");
    }

    public void saveValue(String key, String value){
        sharedPreferences.edit().putString(key,value).apply();
    }
    public void logOut(){
        sharedPreferences.edit().clear().apply();
    }
}

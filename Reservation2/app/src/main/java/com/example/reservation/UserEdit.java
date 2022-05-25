package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.User;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.USERAUTH;
import static com.example.reservation.Constants.updateUserInfo;

public class UserEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();
        final String currentUserName = SharedPreferenceProvider.getInstance().getUserName();
        final String currentUserSurname = SharedPreferenceProvider.getInstance().getUserSurname();
        final String currentUserUsername = SharedPreferenceProvider.getInstance().getUserUsername();
        final String currentUserPassword = SharedPreferenceProvider.getInstance().getUserPassword();
        final String currentUserPhone = SharedPreferenceProvider.getInstance().getUserPhone();
        final String currentUserEmail = SharedPreferenceProvider.getInstance().getUserEmail();

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        EditText editName = findViewById(R.id.editFName);
        editName.setText(currentUserName);
        EditText editSurname = findViewById(R.id.editLName);
        editSurname.setText(currentUserSurname);
        EditText editUsername = findViewById(R.id.editUserName);
        editUsername.setText(currentUserUsername);
        EditText editPassword = findViewById(R.id.editPassword);
        editPassword.setText(currentUserPassword);
        EditText editPhone = findViewById(R.id.editPhone);
        editPhone.setText(currentUserPhone);
        EditText editEmail = findViewById(R.id.editEmail);
        editEmail.setText(currentUserEmail);

        Button change = findViewById(R.id.changeBtn);
        change.setOnClickListener(v -> {

            String firstname = editName.getText().toString();
            String lastname = editSurname.getText().toString();
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String phone = editPhone.getText().toString();
            String email = editEmail.getText().toString();

            String json = "{\"fname\":\"" + firstname + "\", \"lname\":\"" + lastname + "\", \"username\":\"" + username
                    + "\", \"password\":\"" + password + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\"}";

            SharedPreferenceProvider sharedPreferences = SharedPreferenceProvider.getInstance();

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                try {
                    String response = RESTController.sendPut(updateUserInfo(Long.valueOf(currentUserId)), json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Informacija atnaujinta", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            User user = gson.fromJson(response, User.class);
                            sharedPreferences.saveValue("UserId",user.getId());
                            sharedPreferences.saveValue("UserName",user.getFname());
                            sharedPreferences.saveValue("UserSurname",user.getLname());
                            sharedPreferences.saveValue("UserUsername",user.getUsername());
                            sharedPreferences.saveValue("UserPassword",user.getPassword());
                            sharedPreferences.saveValue("UserPhone",user.getPhone());
                            sharedPreferences.saveValue("UserEmail",user.getEmail());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bloga informacija", Toast.LENGTH_SHORT).show();
                            System.out.println(json);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
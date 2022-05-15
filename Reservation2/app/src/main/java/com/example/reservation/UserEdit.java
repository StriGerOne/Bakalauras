package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.utils.SharedPreferenceProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        backButton.setOnClickListener((adapterView) -> {
        finish();
        });

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


            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = Constants.updateUserInfo(Long.valueOf(currentUserId));
                try {
                    String response = RESTController.sendPut(url, json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserEdit.this, UserInfo.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect information", Toast.LENGTH_SHORT).show();
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
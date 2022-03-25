package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.reservation.Constants.USERAUTH;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText LoginUser = findViewById(R.id.login_name);
        EditText LoginPass = findViewById(R.id.login_password);
        Button LoginButton = findViewById(R.id.login_button);
        Button RegisterButton = findViewById(R.id.login_reg_button);

        LoginButton.setOnClickListener(v -> {
            String username = LoginUser.getText().toString();
            String password = LoginPass.getText().toString();

            String json = "{\"login\":\"" + username + "\", \"psw\":\"" + password + "\"}";

            if (username.isEmpty()) {
                LoginUser.setError("Username is required");
                LoginUser.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                LoginPass.setError("Password is required");
                LoginPass.requestFocus();
                return;
            }

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = USERAUTH;
                try {
                    String response = RESTController.sendPost(url, json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainWindow.class);
                            //Kadangi json formatu apir useri info, galit is karto issiparsinti login ar id ir tik ji perduoti
                            //Galima ir visa useri
                            Gson gson = new Gson();
                            User user = gson.fromJson(response, User.class);
                            intent.putExtra("UserInfo", user.getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Bad login or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });
        RegisterButton.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}
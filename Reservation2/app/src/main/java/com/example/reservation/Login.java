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

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


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

            if (username.equals("") || password.equals(""))
                Toast.makeText(Login.this, "Please enter all fields", Toast.LENGTH_SHORT).show();

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = "http://192.168.1.181:8080/authenticate";
                try {
                    String response = RESTController.sendPost(url, json);
                    handler.post(() -> {
                        System.out.println("OK2" + response);
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainWindow.class);
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
package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.User;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import static com.example.reservation.Constants.USERAUTH;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener(view -> startActivity(new Intent(Login.this, MainWindow.class)));

        EditText LoginUser = findViewById(R.id.username);
        EditText LoginPass = findViewById(R.id.password);
        Button LoginButton = findViewById(R.id.loginBtn);
        Button RegisterButton = findViewById(R.id.registerBtn);

        RegisterButton.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));

        LoginButton.setOnClickListener(v -> {
            String username = LoginUser.getText().toString();
            String password = LoginPass.getText().toString();

            String json = "{\"login\":\"" + username + "\", \"psw\":\"" + password + "\"}";

            if (username.isEmpty()) {
                LoginUser.setError("Įveskite vartotojo vardą");
                LoginUser.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                LoginPass.setError("Įveskite slaptažodį");
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
                            Toast.makeText(getApplicationContext(), "Sėkmingai prisijungta", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainWindow.class);
                            Gson gson = new Gson();
                            User user = gson.fromJson(response, User.class);
                            intent.putExtra("UserId", user.getId());
                            intent.putExtra("UserName", user.getFname());
                            intent.putExtra("UserSurname", user.getLname());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Neteisingas vartotojo vardas arba slaptažodis", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });
    }
}
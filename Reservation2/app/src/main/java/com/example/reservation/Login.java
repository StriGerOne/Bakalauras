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
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import static com.example.reservation.Constants.USERAUTH;


public class Login extends AppCompatActivity {

    private SharedPreferenceProvider sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = SharedPreferenceProvider.getInstance();


        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        EditText loginUserField = findViewById(R.id.username);
        EditText loginPassField = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginBtn);
        Button registerButton = findViewById(R.id.registerBtn);

        registerButton.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));

        loginButton.setOnClickListener(v -> {
            String username = loginUserField.getText().toString();
            String password = loginPassField.getText().toString();

            String json = "{\"login\":\"" + username + "\", \"psw\":\"" + password + "\"}";

            if (username.isEmpty()) {
                loginUserField.setError("Įveskite vartotojo vardą");
                loginUserField.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                loginPassField.setError("Įveskite slaptažodį");
                loginPassField.requestFocus();
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
                            sharedPreferences.saveValue("UserId",user.getId());
                            sharedPreferences.saveValue("UserName",user.getFname());
                            sharedPreferences.saveValue("UserSurname",user.getLname());
                            sharedPreferences.saveValue("UserUsername",user.getUsername());
                            sharedPreferences.saveValue("UserPassword",user.getPassword());
                            sharedPreferences.saveValue("UserPhone",user.getPhone());
                            sharedPreferences.saveValue("UserEmail",user.getEmail());

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
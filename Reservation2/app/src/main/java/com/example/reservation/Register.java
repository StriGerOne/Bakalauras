package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.REGISTER;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        EditText firstNameField = findViewById(R.id.fName);
        EditText lastNameField = findViewById(R.id.lName);
        EditText userNameField = findViewById(R.id.userName);
        EditText passwordField = findViewById(R.id.password);
        EditText phoneField = findViewById(R.id.phone);
        EditText emailField = findViewById(R.id.email);
        Button registrationButton = findViewById(R.id.registerBtn);

        registrationButton.setOnClickListener(v -> {

            String firstname = firstNameField.getText().toString();
            String lastname = lastNameField.getText().toString();
            String username = userNameField.getText().toString();
            String password = passwordField.getText().toString();
            String phone = phoneField.getText().toString();
            String email = emailField.getText().toString();

            String json = "{\"fname\":\"" + firstname + "\", \"lname\":\"" + lastname + "\", \"username\":\"" + username
                    + "\", \"password\":\"" + password + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\"}";


            if (firstname.isEmpty()) {
                firstNameField.setError("Vardo laukas yra privalomas");
                firstNameField.requestFocus();
                return;
            }
            if (!firstname.matches("[a-zA-Z ]+")) {
                firstNameField.requestFocus();
                firstNameField.setError("Neteisingas vardo formatas");
                return;
            }
            if (lastname.isEmpty()) {
                lastNameField.setError("Pavard??s laukas yra privalomas");
                lastNameField.requestFocus();
                return;
            }
            if (!firstname.matches("[a-zA-Z ]+")){
                lastNameField.requestFocus();
                lastNameField.setError("Neteisingas pavard??s formatas");
                return;
            }
            if (username.isEmpty()) {
                userNameField.setError("Vartotojo vardo laukas yra privalomas");
                userNameField.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                passwordField.setError("Slapta??od??io laukas yra privalomas");
                passwordField.requestFocus();
                return;
            }
            if (password.length() < 6) {
                passwordField.setError("Slapta??odis turi b??ti bent i?? 6 simboli??");
                passwordField.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                phoneField.setError("Telefono numerio laukas yra privalomas");
                phoneField.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                emailField.setError("El. pa??to laukas yra privalomas");
                emailField.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailField.setError("Netinkamas el.pa??to formatas");
                emailField.requestFocus();
                return;
            }

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = REGISTER;
                try {
                    String response = RESTController.sendPost(url, json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "S??kmingai u??siregistravote", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Informacija neteisinga", Toast.LENGTH_SHORT).show();
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






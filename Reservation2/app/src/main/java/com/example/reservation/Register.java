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
        backButton.setOnClickListener(view -> startActivity(new Intent(Register.this, Login.class)));

        EditText FirstName = findViewById(R.id.FName);
        EditText LastName = findViewById(R.id.LName);
        EditText UserName = findViewById(R.id.UserName);
        EditText Password = findViewById(R.id.Password);
        EditText Phone = findViewById(R.id.Phone);
        EditText Email = findViewById(R.id.Email);
        Button Registration = findViewById(R.id.registerBtn);

        Registration.setOnClickListener(v -> {

            String firstname = FirstName.getText().toString();
            String lastname = LastName.getText().toString();
            String username = UserName.getText().toString();
            String password = Password.getText().toString();
            String phone = Phone.getText().toString();
            String email = Email.getText().toString();

            String json = "{\"fname\":\"" + firstname + "\", \"lname\":\"" + lastname + "\", \"username\":\"" + username
                    + "\", \"password\":\"" + password + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\"}";


            if (firstname.isEmpty()) {
                FirstName.setError("First name is required");
                FirstName.requestFocus();
                return;
            }
            if (lastname.isEmpty()) {
                LastName.setError("Last name is required");
                LastName.requestFocus();
                return;
            }
            if (username.isEmpty()) {
                UserName.setError("Username is required");
                UserName.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                Password.setError("Password is required");
                Password.requestFocus();
                return;
            }
            if (password.length() < 6) {
                Password.setError("Min password length should be 6 characters!");
                Password.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                Phone.setError("Phone number is required");
                Phone.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                Email.setError("Email number is required");
                Email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Email.setError("Please provide valid email");
                Email.requestFocus();
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
                            Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register.this, Login.class);
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






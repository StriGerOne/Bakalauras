package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.EDITUSER;
import static com.example.reservation.Constants.REGISTER;

public class UserEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        final String currentUserId = getIntent().getStringExtra("UserId");
        final String currentUserName = getIntent().getStringExtra("UserName");
        final String currentUserSurname = getIntent().getStringExtra("UserSurname");
        final String currentUserUsername = getIntent().getStringExtra("UserUsername");
        final String currentUserPassword = getIntent().getStringExtra("UserPassword");
        final String currentUserPhone = getIntent().getStringExtra("UserPhone");
        final String currentUserEmail = getIntent().getStringExtra("UserEmail");

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserEdit.this, UserInfo.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
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

//        change.setOnClickListener((adapterView) -> {
//            System.out.println(currentUserUsername);
//            Intent intent = new Intent(UserEdit.this, MainWindow.class);
//            intent.putExtra("UserId", currentUserId);
//            intent.putExtra("UserName", currentUserId);
//            intent.putExtra("UserSurname", currentUserSurname);
//            intent.putExtra("UserUsername", currentUserUsername);
//            intent.putExtra("UserPassword", currentUserPassword);
//            intent.putExtra("UserPhone", currentUserPhone);
//            intent.putExtra("UserEmail", currentUserEmail);
//            startActivity(intent);
//        });



        change.setOnClickListener(v -> {

            String firstname = editName.getText().toString();
            String lastname = editSurname.getText().toString();
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String phone = editPhone.getText().toString();
            String email = editEmail.getText().toString();

            String json = "{\"fname\":\"" + firstname + "\", \"lname\":\"" + lastname + "\", \"username\":\"" + username
                    + "\", \"password\":\"" + password + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\"}";


//            if (firstname.isEmpty()) {
//                FirstName.setError("First name is required");
//                FirstName.requestFocus();
//                return;
//            }
//            if (lastname.isEmpty()) {
//                LastName.setError("Last name is required");
//                LastName.requestFocus();
//                return;
//            }
//            if (username.isEmpty()) {
//                UserName.setError("Username is required");
//                UserName.requestFocus();
//                return;
//            }
//            if (password.isEmpty()) {
//                Password.setError("Password is required");
//                Password.requestFocus();
//                return;
//            }
//            if (password.length() < 6) {
//                Password.setError("Min password length should be 6 characters!");
//                Password.requestFocus();
//                return;
//            }
//            if (phone.isEmpty()) {
//                Phone.setError("Phone number is required");
//                Phone.requestFocus();
//                return;
//            }
//            if (email.isEmpty()) {
//                Email.setError("Email number is required");
//                Email.requestFocus();
//                return;
//            }
//
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                Email.setError("Please provide valid email");
//                Email.requestFocus();
//                return;
//            }

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = EDITUSER;
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
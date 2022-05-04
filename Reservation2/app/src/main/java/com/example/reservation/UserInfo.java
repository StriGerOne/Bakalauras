package com.example.reservation;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final String currentUserId = getIntent().getStringExtra("UserId");
        final String currentUserName = getIntent().getStringExtra("UserName");
        final String currentUserSurname = getIntent().getStringExtra("UserSurname");
        final String currentUserUsername = getIntent().getStringExtra("UserUsername");
        final String currentUserPassword = getIntent().getStringExtra("UserPassword");
        final String currentUserPhone = getIntent().getStringExtra("UserPhone");
        final String currentUserEmail = getIntent().getStringExtra("UserEmail");

        ImageButton backButton = findViewById(R.id.back);
        Button logOff = findViewById(R.id.logOutBtn);
        Button userReservation = findViewById(R.id.yourReservationBtn);
        Button editUser = findViewById(R.id.userInfoBtn);

        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, MainWindow.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("UserName", currentUserName);
            intent.putExtra("UserSurname", currentUserSurname);
            startActivity(intent);
        });

        userReservation.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, UserReservations.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });

        editUser.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, UserEdit.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("UserName", currentUserName);
            intent.putExtra("UserSurname", currentUserSurname);
            intent.putExtra("UserUsername", currentUserUsername);
            intent.putExtra("UserPassword", currentUserPassword);
            intent.putExtra("UserPhone", currentUserPhone);
            intent.putExtra("UserEmail", currentUserEmail);
            startActivity(intent);
        });

        logOff.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, MainWindow.class);
            Toast.makeText(getApplicationContext(), "Atsijungta sėkmingai", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });




        
    }
}
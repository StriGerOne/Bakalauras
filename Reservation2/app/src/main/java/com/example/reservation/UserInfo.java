package com.example.reservation;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.utils.SharedPreferenceProvider;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        Button editUser = findViewById(R.id.userInfoBtn);
        editUser.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, UserEdit.class);
            startActivity(intent);

        });
        Button userRatings = findViewById(R.id. yourReitingBtn);
        userRatings.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, UserRatings.class);
            startActivity(intent);
        });

        Button userReservation = findViewById(R.id.yourReservationBtn);
        userReservation.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, UserReservations.class);
            startActivity(intent);
        });

        Button logOff = findViewById(R.id.logOutBtn);
        logOff.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserInfo.this, MainWindow.class);
            Toast.makeText(getApplicationContext(), "Atsijungta sėkmingai", Toast.LENGTH_SHORT).show();
            SharedPreferenceProvider.getInstance().logOut();
            startActivity(intent);
        });
    }
}
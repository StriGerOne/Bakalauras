package com.example.reservation;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RestaurantDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauran_details);

        final String currentUserId = getIntent().getStringExtra("UserId");
        final String currentUserName = getIntent().getStringExtra("UserName");
        final String currentUserSurname = getIntent().getStringExtra("UserSurname");

        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);
        final String currentRestaurantName = getIntent().getStringExtra("RestaurantName");
        final String currentRestaurantAddress = getIntent().getStringExtra("RestaurantAddress");
        final String currentRestaurantPhone = getIntent().getStringExtra("RestaurantPhone");
        final String currentRestaurantEmail = getIntent().getStringExtra("RestaurantEmail");
        final String currentRestaurantSummary = getIntent().getStringExtra("RestaurantSummary");

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, MainWindow.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });

        TextView restName = findViewById(R.id.restNameField);
        restName.setText(currentRestaurantName);
        TextView restAddress = findViewById(R.id.restAddressField);
        restAddress.setText(currentRestaurantAddress);
        TextView restPhone = findViewById(R.id.restPhoneField);
        restPhone.setText(currentRestaurantPhone);
        TextView restEmail = findViewById(R.id.restEmailField);
        restEmail.setText(currentRestaurantEmail);
        TextView restSummary = findViewById(R.id.restSummaryField);
        restSummary.setText(currentRestaurantSummary);

        Button rate = findViewById(R.id.rate);
        rate.setOnClickListener(view -> startActivity(new Intent(RestaurantDetails.this, RestaurantRating.class)));

        Button reservate = findViewById(R.id.reservateB);
        reservate.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, ReservationForm.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("UserName", currentUserName);
            intent.putExtra("UserSurname", currentUserSurname);
            intent.putExtra("RestaurantId", currentRestaurantId);
            intent.putExtra("RestaurantName", currentRestaurantName);
            startActivity(intent);
        });
    }
}
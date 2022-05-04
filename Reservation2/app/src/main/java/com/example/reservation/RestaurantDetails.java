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

        ImageButton backButton = findViewById(R.id.back);
        TextView restName = findViewById(R.id.restNameField);
        TextView restAddress = findViewById(R.id.restAddressField);
        TextView restPhone = findViewById(R.id.restPhoneField);
        TextView restEmail = findViewById(R.id.restEmailField);
        TextView restSummary = findViewById(R.id.restSummaryField);
        Button reservate = findViewById(R.id.reservateB);

        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, MainWindow.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });

    }
}
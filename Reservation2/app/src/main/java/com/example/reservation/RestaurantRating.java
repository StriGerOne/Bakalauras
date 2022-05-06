package com.example.reservation;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RestaurantRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_rating);

        final String currentUserId = getIntent().getStringExtra("UserId");

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantRating.this, RestaurantDetails.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });

        RatingBar restRating = findViewById(R.id.restRating);
        EditText restComment = findViewById(R.id.restComment);
        Button ratingConfirm = findViewById(R.id.confirm);

        ratingConfirm.setOnClickListener(view -> {
            String rating = String.valueOf(restRating.getRating());
            Toast.makeText(getApplicationContext(),rating+ "Stars", Toast.LENGTH_SHORT).show();
        });
    }
}
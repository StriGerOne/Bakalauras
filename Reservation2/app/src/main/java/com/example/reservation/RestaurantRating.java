package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RATE;

public class RestaurantRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_rating);

        final String currentUserId = getIntent().getStringExtra("UserId");
        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);


        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantRating.this, RestaurantDetails.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("RestaurantId", currentRestaurantId);
            startActivity(intent);
        });


        RatingBar restRatingBar = findViewById(R.id.restRating);
        EditText restCommentField = findViewById(R.id.restCommentField);
        Button ratingConfirm = findViewById(R.id.confirm);

        ratingConfirm.setOnClickListener(view -> {


            Float rating = restRatingBar.getRating();
            String comment = restCommentField.getText().toString();


            Toast.makeText(getApplicationContext(),rating+ "Stars", Toast.LENGTH_SHORT).show();

            String json = "{\"rating\":\"" + rating + "\", \"comment\":\"" + comment + "\", \"userId\":\"" + currentUserId
                    + "\", \"restaurantId\":\"" + currentRestaurantId +"\"}";


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String url = RATE;
            try {
                String response = RESTController.sendPost(url, json);
                handler.post(() -> {
                    if (!response.equals("Error") && !response.equals("")) {
                        Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RestaurantRating.this, RestaurantDetails.class);
                        intent.putExtra("UserId", currentUserId);
                        intent.putExtra("Comment", comment);
                        intent.putExtra("Rating", rating);
                        intent.putExtra("RestaurantId", currentRestaurantId);
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
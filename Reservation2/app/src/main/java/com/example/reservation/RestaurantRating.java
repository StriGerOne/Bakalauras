package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.utils.SharedPreferenceProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.NEWRATE;

public class RestaurantRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_rating);

        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();
        final String currentUsername = SharedPreferenceProvider.getInstance().getUserUsername();

        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);
        final String currentRestaurantName = getIntent().getStringExtra("RestaurantName");
        final String currentRestaurantAddress = getIntent().getStringExtra("RestaurantAddress");
        final String currentRestaurantPhone = getIntent().getStringExtra("RestaurantPhone");
        final String currentRestaurantEmail = getIntent().getStringExtra("RestaurantEmail");
        final String currentRestaurantSummary = getIntent().getStringExtra("RestaurantSummary");
        final String currentRestaurantImage = getIntent().getStringExtra("RestaurantImage");


        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        RatingBar restRatingBar = findViewById(R.id.restRating);
        EditText restCommentField = findViewById(R.id.restCommentField);
        Button ratingConfirm = findViewById(R.id.confirm);

        ratingConfirm.setOnClickListener(view -> {

            Float rating = restRatingBar.getRating();
            String comment = restCommentField.getText().toString();


            String json = "{\"rating\":\"" + rating + "\", \"comment\":\"" + comment + "\", \"userId\":\"" + currentUserId
                    + "\", \"username\":\"" + currentUsername + "\", \"restaurantId\":\"" + currentRestaurantId +"\", \"restName\":\"" + currentRestaurantName +"\"}";

            if (comment.isEmpty()) {
                restCommentField.setError("Para??ykite savo nuomon??");
                restCommentField.requestFocus();
                return;
            }
            if (comment.length() < 15 ) {
                restCommentField.setError("Komentras turi b??ti bent i?? 30 simboli??");
                restCommentField.requestFocus();
                return;
            }

            if(rating < 1.0f){
                restRatingBar.setRating(1.0f);
                return; }

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String url = NEWRATE;
            try {
                String response = RESTController.sendPost(url, json);
                handler.post(() -> {
                    if (!response.equals("Error") && !response.equals("")) {
                        Toast.makeText(getApplicationContext(), "??vertinimas ??ra??ytas", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RestaurantRating.this, RestaurantDetails.class);
                        intent.putExtra("Comment", comment);
                        intent.putExtra("Rating", rating);
                        intent.putExtra("RestaurantId", currentRestaurantId);
                        intent.putExtra("RestaurantName", currentRestaurantName);
                        intent.putExtra("RestaurantAddress", currentRestaurantAddress);
                        intent.putExtra("RestaurantPhone", currentRestaurantPhone);
                        intent.putExtra("RestaurantEmail", currentRestaurantEmail);
                        intent.putExtra("RestaurantSummary", currentRestaurantSummary);
                        intent.putExtra("RestaurantImage", currentRestaurantImage);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Neteisinga informacija", Toast.LENGTH_SHORT).show();
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
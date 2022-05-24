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
        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);


        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        RatingBar restRatingBar = findViewById(R.id.restRating);
        EditText restCommentField = findViewById(R.id.restCommentField);
        Button ratingConfirm = findViewById(R.id.confirm);

        ratingConfirm.setOnClickListener(view -> {

            Float rating = restRatingBar.getRating();
            String comment = restCommentField.getText().toString();


            String json = "{\"rating\":\"" + rating + "\", \"comment\":\"" + comment + "\", \"userId\":\"" + currentUserId
                    + "\", \"restaurantId\":\"" + currentRestaurantId +"\"}";

            if (comment.isEmpty()) {
                restCommentField.setError("Parašykite savo nuomonę");
                restCommentField.requestFocus();
                return;
            }
            if (comment.length() < 30 ) {
                restCommentField.setError("Komentras turi būti bent iš 30 simbolių");
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
                        Toast.makeText(getApplicationContext(), "Įvertinimas įrašytas", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RestaurantRating.this, RestaurantDetails.class);
                        intent.putExtra("Comment", comment);
                        intent.putExtra("Rating", rating);
                        intent.putExtra("RestaurantId", currentRestaurantId);
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
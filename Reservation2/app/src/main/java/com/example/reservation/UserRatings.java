package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Rating;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RATELIST;
import static com.example.reservation.Constants.getReservationsByUser;

public class UserRatings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ratings);

        final String currentUserId = getIntent().getStringExtra("UserId");

        ImageButton backButton = findViewById(R.id.back);

        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserRatings.this, UserInfo.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RATELIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {

                    if (!response.equals("") && !response.equals("Error")) {
                        Gson builder = new GsonBuilder().create();
                        Type ratingListType = new TypeToken<List<Rating>>() {
                        }.getType();
                        final List<Rating> ratingListFromJson = builder.fromJson(response, ratingListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        List<String> ratingList = new ArrayList<>();
                        ratingListFromJson.forEach(r->ratingList.add(r.getRating() + " " + r.getComment()));

                        ListView rateList = findViewById(R.id.ratesList);

                        ArrayAdapter<Rating> arrayAdapter = new ArrayAdapter<>(UserRatings.this, android.R.layout.simple_list_item_1, ratingListFromJson);
                        rateList.setAdapter(arrayAdapter);
                    }

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Rating;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.Serializer.LocalDateTimeGsonSerializer;
import com.example.reservation.utils.RatingAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class RestaurantDetails extends AppCompatActivity {
    private RatingAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restauran_details);

        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);
        final String currentRestaurantName = getIntent().getStringExtra("RestaurantName");
        final String currentRestaurantAddress = getIntent().getStringExtra("RestaurantAddress");
        final String currentRestaurantPhone = getIntent().getStringExtra("RestaurantPhone");
        final String currentRestaurantEmail = getIntent().getStringExtra("RestaurantEmail");
        final String currentRestaurantSummary = getIntent().getStringExtra("RestaurantSummary");

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

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
        rate.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, RestaurantRating.class);
            intent.putExtra("RestaurantId", currentRestaurantId);
            startActivity(intent);
        });

        Button reservate = findViewById(R.id.reservateBtn);
        reservate.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, ReservationForm.class);
            intent.putExtra("RestaurantId", currentRestaurantId);
            intent.putExtra("RestaurantName", currentRestaurantName);
            startActivity(intent);
        });

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = Constants.getRateByRestaurant(currentRestaurantId);
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {

                    if (!response.equals("") && !response.equals("Error")) {

                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(LocalTime.class, new DataTimeSerializer());
                        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer());
                        Type ratingListType = new TypeToken<List<Rating>>() {
                        }.getType();
                        final List<Rating> ratingListFromJson = builder.create().fromJson(response, ratingListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        /** Spausdina visą info esančią restourant klasėje **/
                        ListView ratingList = findViewById(R.id.ratesList);
                        adapter = new RatingAdapter(this, ratingListFromJson);
                        ratingList.setAdapter(adapter);
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
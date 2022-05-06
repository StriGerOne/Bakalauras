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

public class RestaurantDetails extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
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
        rate.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, RestaurantRating.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("RestaurantId", currentRestaurantId);
            startActivity(intent);
        });
        
        Button reservate = findViewById(R.id.reservateBtn);
        reservate.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(RestaurantDetails.this, ReservationForm.class);
            intent.putExtra("UserId", currentUserId);
            intent.putExtra("UserName", currentUserName);
            intent.putExtra("UserSurname", currentUserSurname);
            intent.putExtra("RestaurantId", currentRestaurantId);
            intent.putExtra("RestaurantName", currentRestaurantName);
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

                        ArrayAdapter<Rating> arrayAdapter = new ArrayAdapter<>(RestaurantDetails.this, android.R.layout.simple_list_item_1, ratingListFromJson);
                        rateList.setAdapter(arrayAdapter);
                    }

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


    }
}
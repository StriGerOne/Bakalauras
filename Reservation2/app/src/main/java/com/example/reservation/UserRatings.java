package com.example.reservation;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Rating;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.Serializer.LocalDateTimeGsonSerializer;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRatings extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ratings);

        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            String url = Constants.getRateByUser(Long.valueOf(currentUserId));
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
                        List<String> ratingList = new ArrayList<>();
                        ratingListFromJson.forEach(r->ratingList.add(r.getRating() + " " + r.getComment()));

                        ListView rateList = findViewById(R.id.userRateList);

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
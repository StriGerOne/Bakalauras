package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Reservation;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.Serializer.LocalDateTimeGsonSerializer;
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

import static com.example.reservation.Constants.RESERVATIONLIST;

public class UserReservations extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        final String currentUserId = getIntent().getStringExtra("UserId");

        ImageButton backButton = findViewById(R.id.back);

        backButton.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(UserReservations.this, UserInfo.class);
            intent.putExtra("UserId", currentUserId);
            startActivity(intent);
        });


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RESERVATIONLIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {

                    if (!response.equals("") && !response.equals("Error")) {

                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(LocalTime.class, new DataTimeSerializer());
                        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer());
                        Type reservationListType = new TypeToken<List<Reservation>>() {
                        }.getType();
                        final List<Reservation> reservationListFromJson = builder.create().fromJson(response, reservationListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        List<String> reservationList = new ArrayList<>();
                        reservationListFromJson.forEach(r->reservationList.add(r.getPeopleAmount() + " " + r.getReservationTime()));

                        ListView test = findViewById(R.id.activeReservationList);

                        ArrayAdapter<Reservation> arrayAdapter = new ArrayAdapter<>(UserReservations.this, android.R.layout.simple_list_item_1, reservationListFromJson);
                        test.setAdapter(arrayAdapter);
                    }

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
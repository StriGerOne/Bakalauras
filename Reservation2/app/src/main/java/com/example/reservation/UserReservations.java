package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Reservation;
import com.example.reservation.Models.Restaurant;
import com.example.reservation.utils.CustomListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.*;

public class UserReservations extends AppCompatActivity {

    private CustomListAdapter adapt;

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
                        Gson builder = new GsonBuilder().create();
                        Type ReservationListType = new TypeToken<List<Reservation>>() {
                        }.getType();
                        final List<Reservation> reservationListFromJson = builder.fromJson(response, ReservationListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        List<String> reservationList = new ArrayList<>();
                        reservationListFromJson.forEach(r->reservationList.add(r.getPeopleAmount() + " " + r.getReservationTime()));

                        ListView test = findViewById(R.id.reservationList);

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
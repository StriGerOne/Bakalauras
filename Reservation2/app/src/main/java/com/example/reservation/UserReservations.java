package com.example.reservation;

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

public class UserReservations extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = Constants.getReservationsByUser(Long.valueOf(currentUserId));
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
                        List<String> reservationList = new ArrayList<>();
                        reservationListFromJson.forEach(r->reservationList.add(r.getPeopleAmount() + " " + r.getReservationTime()));
                        List<Reservation> approvedReservations = new ArrayList<>();

                        List<Reservation> canceledReservations = new ArrayList<>();

                        for (Reservation reservation : reservationListFromJson) {
                            String status = reservation.getStatus();
                            if (status.equals("Patvirtinta")){
                                approvedReservations.add(reservation);
                            }
                            if (status.equals("Atšaukta")){
                                canceledReservations.add(reservation);
                            }
                        }

                        List<Reservation> fullList = new ArrayList<>();
                        fullList.addAll(approvedReservations);
                        fullList.addAll(canceledReservations);

                        ListView test = findViewById(R.id.activeReservationList);

                        ArrayAdapter<Reservation> arrayAdapter = new ArrayAdapter<>(UserReservations.this, android.R.layout.simple_list_item_1, fullList);
                        test.setAdapter(arrayAdapter);
                    }

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Tables;
import com.example.reservation.Serializer.LocalDateTimeGsonSerializer;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.*;

public class SelectSeat extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);


        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();

        final Long restaurantId = getIntent().getLongExtra("RestaurantId", 0);
        final String currentPeopleAmount = getIntent().getStringExtra("peopleAmount");
        final String currentreservationTime = getIntent().getStringExtra("reservationTime");
        final String currentDuration = getIntent().getStringExtra("duration");

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

            String url = Constants.getFreeTables(String.valueOf(currentreservationTime), String.valueOf(currentDuration),
                    Long.valueOf(restaurantId), Integer.valueOf(currentPeopleAmount));
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {

                    if (!response.equals("") && !response.equals("Error")) {

                        GsonBuilder builder = new GsonBuilder();
                        Type tableListType = new TypeToken<List<Tables>>() {
                        }.getType();
                        final List<Tables> tableListFromJson = builder.create().fromJson(response, tableListType);

                        List<String> tableList = new ArrayList<>();
                        tableListFromJson.forEach(r -> tableList.add(String.valueOf(r.getSeatAmount())));

                        ListView tablesList = findViewById(R.id.seatList);

                        ArrayAdapter<Tables> arrayAdapter = new ArrayAdapter<>(SelectSeat.this, android.R.layout.simple_list_item_single_choice, tableListFromJson);
                        tablesList.setAdapter(arrayAdapter);

                        tablesList.setOnItemClickListener((adapterView, view, i, l) -> {
                            Button confirm = findViewById(R.id.selectBtn);
                            confirm.setOnClickListener(v -> {

                                String id = tableListFromJson.get(i).getTableId();

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer());
                                Properties dataToSend = new Properties();
                                dataToSend.setProperty("peopleAmount", currentPeopleAmount);
                                dataToSend.setProperty("reservationTime", LocalDateTime.parse(currentreservationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString());
                                dataToSend.setProperty("duration", currentDuration);
                                dataToSend.setProperty("restaurantId", String.valueOf(restaurantId));
                                dataToSend.setProperty("userId", currentUserId);
                                dataToSend.setProperty("selectedSeat", id);

                                Executor executorForReservation = Executors.newSingleThreadExecutor();
                                Handler handlerForReservation = new Handler(Looper.getMainLooper());
                                executorForReservation.execute(() -> {
                                    try {
                                        String response2 = RESTController.sendPost(RESERVATE, gsonBuilder.create().toJson(dataToSend, Properties.class));
                                        handlerForReservation.post(() -> {
                                            if (!response2.equals("Error") && !response2.equals("")) {
                                                Toast.makeText(getApplicationContext(), "Rezervacija atlikta sėkmingai", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SelectSeat.this, MainWindow.class);
                                                startActivity(intent);

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Neteisinga informacija", Toast.LENGTH_SHORT).show();
                                                System.out.println(dataToSend);
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            });
                        });
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
}

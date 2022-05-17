package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Tables;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.TABLES;
import static com.example.reservation.Constants.getTablesByRestaurant;

public class SelectSeat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);



        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();
        final Long currentRestaurantId = getIntent().getLongExtra("RestaurantId", 0);


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {

           // String url = Constants.getTablesByRestaurant(currentRestaurantId);
            String url = TABLES;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {

                    if (!response.equals("") && !response.equals("Error")) {

                        GsonBuilder builder = new GsonBuilder();
                        Type tableListType = new TypeToken<List<Tables>>() {
                        }.getType();
                        final List<Tables> tableListFromJson = builder.create().fromJson(response, tableListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        List<String> tableList = new ArrayList<>();
                        tableListFromJson.forEach(r->tableList.add(String.valueOf(r.getSeatAmount())));

                        ListView tablesList = findViewById(R.id.seatList);

                        ArrayAdapter<Tables> arrayAdapter = new ArrayAdapter<>(SelectSeat.this, android.R.layout.simple_list_item_single_choice, tableListFromJson);
                        tablesList.setAdapter(arrayAdapter);

                        tablesList.setOnItemClickListener((adapterView, view, i, l) -> {

                            String id = tableListFromJson.get(i).getTableId();
                            Button confirm = findViewById(R.id.selectBtn);
                            confirm.setOnClickListener(v -> System.out.println(id));
                        });
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
}

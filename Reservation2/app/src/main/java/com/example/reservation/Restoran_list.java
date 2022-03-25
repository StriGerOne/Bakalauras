package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESTLIST;

public class Restoran_list extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_list);//bfdhgdsgf

        Button pick_date_time = findViewById(R.id.Reservate);

        pick_date_time.setOnClickListener(v -> startActivity(new Intent(Restoran_list.this, ReservationForm.class)));

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RESTLIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {
                    if (!response.equals("") && !response.equals("Error")) {

                        Gson builder = new GsonBuilder().create();

                        Type RestouranListType = new TypeToken<List<Restoran_list>>() {
                        }.getType();
                        final List<Restoran_list> restoranListFromJson = builder.fromJson(response, RestouranListType);

                        ListView Restoran_List = findViewById(R.id.restlist);

                        ArrayAdapter<Restoran_list> arrayAdapter = new ArrayAdapter<>(Restoran_list.this, android.R.layout.simple_list_item_1, restoranListFromJson);
                        Restoran_List.setAdapter(arrayAdapter);

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
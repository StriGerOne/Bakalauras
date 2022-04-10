package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Restourant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESTLIST;

public class RestoranList extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_list);

        Button pick_date_time = findViewById(R.id.Reservate);

        pick_date_time.setOnClickListener(v -> startActivity(new Intent(RestoranList.this, ReservationForm.class)));

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RESTLIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {
                    if (!response.equals("") && !response.equals("Error")) {

                        Gson builder = new GsonBuilder().create();

                        Type RestouranListType = new TypeToken<List<Restourant>>() {
                        }.getType();
                        final List<Restourant> restoranListFromJson = builder.fromJson(response, RestouranListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                       /* List<String> resList = new ArrayList<>();
                        restoranListFromJson.forEach(r->resList.add(r.getPhone() + " " + r.getEmail()));

                        ListView Restoran_List = findViewById(R.id.restlist);

                        ArrayAdapter<Restourant> arrayAdapter = new ArrayAdapter<>(RestoranList.this, android.R.layout.simple_list_item_1, restoranListFromJson);
                        Restoran_List.setAdapter(arrayAdapter);*/
                        /** Spausdina tik nurodytą Stringą **/

                        List<String> resList = new ArrayList<>();
                        restoranListFromJson.forEach(r->resList.add(r.getPhone()));

                        ListView Restoran_List = findViewById(R.id.restlist);
                        //TextView test = findViewById(R.id.textView);

                        ListAdapter listAdapter = new ArrayAdapter<>(RestoranList.this, android.R.layout.simple_list_item_1, resList);
                        Restoran_List.setAdapter(listAdapter);
                        
                    }
                    
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
      //  Restoran_List.setOnClickListener(v -> startActivity(new Intent(RestoranList.this, ReservationForm.class)));
    }
}
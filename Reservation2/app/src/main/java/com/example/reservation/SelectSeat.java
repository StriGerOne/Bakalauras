package com.example.reservation;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
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

            String url = Constants.getTablesByRestaurant(currentRestaurantId);
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

                        ArrayAdapter<Tables> arrayAdapter = new ArrayAdapter<>(SelectSeat.this, android.R.layout.simple_list_item_1, tableListFromJson);
                        tablesList.setAdapter(arrayAdapter);
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });




//        executor.execute(() -> {
//
//            String url = Constants.getTablesByRestaurant(Long.valueOf(currentRestaurantId));
//            try {
//                String response = RESTController.sendGet(url);
//                handler.post(() -> {
//
//                    if (!response.equals("") && !response.equals("Error")) {
//
//                        GsonBuilder builder = new GsonBuilder();
//                        Type tablesListType = new TypeToken<List<Tables>>() {
//                        }.getType();
//                        final List<Tables> tablesListFromJson = builder.create().fromJson(response, tablesListType);
//                        /** Spausdina visą info esančią restourant klasėje **/
//                        List<String> tableList = new ArrayList<>();
//                        tablesListFromJson.forEach(r->tableList.add(r.getSeatAmount() + " " + r.getTableId()));
//
//                        ListView seatList = findViewById(R.id.seatList);
//
//                        ArrayAdapter<Tables> arrayAdapter = new ArrayAdapter<>(SelectSeat.this, android.R.layout.simple_list_item_1, tablesListFromJson);
//                        seatList.setAdapter(arrayAdapter);
//                    }
//                });
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        });
    }
}




/*
        uInput = findViewById(R.id.ed_name);

        linearLayout = findViewById(R.id.linear_layout);

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {
            String name = uInput.getText().toString();
            if (!name.isEmpty()) {
                CheckBox checkBox = new CheckBox(SelectSeat.this);
                checkBox.setText(name);
                linearLayout.addView(checkBox);
            } else
                Toast.makeText(SelectSeat.this, "The name cannot be empty!", Toast.LENGTH_LONG).show();
        });
*/
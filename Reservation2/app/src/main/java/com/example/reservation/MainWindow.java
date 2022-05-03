package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Restaurant;
import com.example.reservation.utils.CustomListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESTLIST;

public class MainWindow extends AppCompatActivity {
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String currentUserId = getIntent().getStringExtra("UserId");
        final String currentUserName = getIntent().getStringExtra("UserName");
        final String currentUserSurname = getIntent().getStringExtra("UserSurname");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton loginUserBoard = findViewById(R.id.userBoardBtn);
        loginUserBoard.setOnClickListener((adapterView) -> {
                    Intent intent = new Intent(MainWindow.this, UserInfo.class);
                    intent.putExtra("UserId", currentUserId);
                    if (currentUserId != null) {
                        startActivity(intent);
                    } else
                        startActivity(new Intent(MainWindow.this, Login.class));
                });

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RESTLIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {
                    if (!response.equals("") && !response.equals("Error")) {
                        Gson builder = new GsonBuilder().create();
                        Type restaurantListType = new TypeToken<List<Restaurant>>() {
                        }.getType();
                        final List<Restaurant> restaurantListFromJson = builder.fromJson(response, restaurantListType);
                        ListView restaurantList = findViewById(R.id.restaurantList);
                        adapter = new CustomListAdapter(this, restaurantListFromJson);
                        restaurantList.setAdapter(adapter);
                        restaurantList.setOnItemClickListener((adapterView, view, i, l) -> {
                            Intent intent = new Intent(MainWindow.this, ReservationForm.class);
                            intent.putExtra("UserId", currentUserId);
                            intent.putExtra("RestaurantId", restaurantListFromJson.get(i).getId());
                            intent.putExtra("RestaurantName", restaurantListFromJson.get(i).getRestaurantName());
                            intent.putExtra("UserName", currentUserName);
                            intent.putExtra("UserSurname", currentUserSurname);
                            if (currentUserId != null) {
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "Pirmiausiai turite prisijungti", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}



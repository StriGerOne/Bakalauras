package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Restaurant;
import com.example.reservation.utils.RestaurantAdapter;
import com.example.reservation.utils.SharedPreferenceProvider;
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
    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String currentUserId = SharedPreferenceProvider.getInstance().getUserId();

        ImageButton loginUserBoard = findViewById(R.id.userBoardBtn);
        loginUserBoard.setOnClickListener((adapterView) -> {
            Intent intent = new Intent(MainWindow.this, UserInfo.class);

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
                        adapter = new RestaurantAdapter(this, restaurantListFromJson);
                        restaurantList.setAdapter(adapter);
                        restaurantList.setOnItemClickListener((adapterView, view, i, l) -> {
                            Intent intent = new Intent(MainWindow.this, RestaurantDetails.class);

                            intent.putExtra("RestaurantId", restaurantListFromJson.get(i).getId());
                            intent.putExtra("RestaurantName", restaurantListFromJson.get(i).getRestaurantName());
                            intent.putExtra("RestaurantAddress", restaurantListFromJson.get(i).getAddress());
                            intent.putExtra("RestaurantPhone", restaurantListFromJson.get(i).getPhone());
                            intent.putExtra("RestaurantEmail", restaurantListFromJson.get(i).getEmail());
                            intent.putExtra("RestaurantSummary", restaurantListFromJson.get(i).getSummary());

                            if (currentUserId != null) {
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "Pirmiausiai turite prisijungti", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

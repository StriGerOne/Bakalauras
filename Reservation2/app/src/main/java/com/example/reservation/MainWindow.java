package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.Restourant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESTLIST;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton Login_Userboard = findViewById(R.id.login_userboard);
        Login_Userboard.setOnClickListener(view -> startActivity(new Intent(MainWindow.this, Login.class)));


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String url = RESTLIST;
            try {
                String response = RESTController.sendGet(url);
                handler.post(() -> {


                    if (!response.equals("") && !response.equals("Error")) {
                        Toast.makeText(MainWindow.this.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainWindow.this, ReservationForm.class);
                        Gson builder = new GsonBuilder().create();
                        Type RestouranListType = new TypeToken<List<Restourant>>() {
                        }.getType();
                        final List<Restourant> restoranListFromJson = builder.fromJson(response, RestouranListType);
                        /** Spausdina visą info esančią restourant klasėje **/
                        List<String> resList = new ArrayList<>();
                        restoranListFromJson.forEach(r->resList.add(r.getPhone() + " " + r.getEmail()));

                        ListView Restoran_List = findViewById(R.id.restlist);

                        ArrayAdapter<Restourant> arrayAdapter = new ArrayAdapter<>(MainWindow.this, android.R.layout.simple_list_item_1, restoranListFromJson);
                        Restoran_List.setAdapter(arrayAdapter);
                        /** Spausdina tik nurodytą Stringą **/
//                        List<String> resList = new ArrayList<>();
//                        restoranListFromJson.forEach(r -> resList.add(r.getPhone()));
//
//                        ListView Restoran_List = findViewById(R.id.restlist);
//
//                        ListAdapter listAdapter = new ArrayAdapter<>(MainWindow.this, android.R.layout.simple_list_item_1, resList);
//                        Restoran_List.setAdapter(listAdapter);

                        Restoran_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(MainWindow.this, ReservationForm.class);

                                Intent currentIntent = getIntent();
                                String userId = currentIntent.getStringExtra("UserInfo");
                                if(userId != null) {
                                    startActivity(intent);
                                System.out.println(userId);
                                }
                                else Toast.makeText(getApplicationContext(), "You need to login first", Toast.LENGTH_SHORT).show();

                            }

                        });

                    }

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
    }



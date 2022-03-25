package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;





public class MainWindow extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent currentIntent = getIntent();
        String userId = currentIntent.getStringExtra("UserInfo");
        if(userId != null) {

        }

        System.out.println(userId);

        ImageButton Login_Userboard = findViewById(R.id.login_userboard);

        Login_Userboard.setOnClickListener(view -> startActivity(new Intent(MainWindow.this, Login.class)));

        Button RestList = findViewById(R.id.list);

        RestList.setOnClickListener(view -> startActivity(new Intent(MainWindow.this, RestoranList.class)));


    }

}


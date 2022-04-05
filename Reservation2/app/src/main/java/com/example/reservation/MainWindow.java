package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class MainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView login = findViewById(R.id.login_userboard);


        Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png")
                .into(login);

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


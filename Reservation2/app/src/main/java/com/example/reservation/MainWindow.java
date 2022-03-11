package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;





public class MainWindow extends AppCompatActivity {

    ImageButton Login_Userboard;
    ImageButton Pica_Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login_Userboard = (ImageButton) findViewById(R.id.login_userboard);

        Login_Userboard.setOnClickListener(view -> startActivity(new Intent(MainWindow.this, Login.class)));

        Pica_Category = (ImageButton) findViewById(R.id.pica_category);

        Pica_Category.setOnClickListener(view -> startActivity(new Intent(MainWindow.this, Restoran_list.class)));
    }

}


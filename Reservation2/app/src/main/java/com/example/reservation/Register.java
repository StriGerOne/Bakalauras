package com.example.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText FirstName = findViewById(R.id.FName);
        EditText LastName = findViewById(R.id.LName);
        EditText UserName = findViewById(R.id.UserName);
        EditText Password = findViewById(R.id.Password);
        EditText Phone = findViewById(R.id.Phone);
        EditText Email = findViewById(R.id.Email);
        Button Registration = findViewById(R.id.btn_register);
        Button Back_ToLogin = findViewById(R.id.btn_reg_back);


        Registration.setOnClickListener(v -> {
            String firstname = FirstName.getText().toString();
            String lastname = LastName.getText().toString();
            String username = UserName.getText().toString();
            String password = Password.getText().toString();
            String phone = Phone.getText().toString();
            String emai = Email.getText().toString();


/*
            String json = "{\"FirstName\"" + FirstName.getText().toString() + "\", \"LastName\"" + LastName.getText().toString() + "\", \"UserName\"" + UserName.getText().toString()
                    + "\", \"Password\"" + Password.getText().toString() + "\", \"Phone\"" + Phone.getText().toString() + "\", \"Email\"" + Email.getText().toString() + "\"}";
*/                  /**
             Ar užpildyti visi laukai
             */
            if (firstname.equals("") || lastname.equals("") || username.equals("") || password.equals("") || phone.equals("") || emai.equals("")) {
                Toast.makeText(Register.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }

  /*          if( ) {

                 Jei įvesti laukai atitinka laukų tipus -> Įrašas įrašomas į duomenų bazę

                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }*/
                    /**
                     Jei įvesti laukai atitinka laukų tipus -> Įrašas įrašomas į duomenų bazę
                     */
                    else{
                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

        });
    }
}

//            Executor executor = Executors.newSingleThreadExecutor();
//            Handler handler = new Handler(Looper.getMainLooper());
//            String[] response = {""};
//
//            executor.execute(() -> {
//                String url = "http://192.168.8.101:8080/addUser";
//                try {
//                    response[0] = RESTController.sendPost(url, json);
//                    System.out.println(response[0]);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            });
//            handler.post(() -> {
//                System.out.println(response[0]);
//                if (response[0] != null) {
//                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Register.this, Login.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Bad information", Toast.LENGTH_SHORT).show();
//                }
//            });

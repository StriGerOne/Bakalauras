package com.example.reservation;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import com.example.reservation.Controllers.RESTController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESERVATE;

public class ReservationForm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        EditText People_amount = findViewById(R.id.amount);
        EditText Date_time_in=findViewById(R.id.date_time_input);
        Date_time_in.setInputType(InputType.TYPE_NULL);
        Date_time_in.setOnClickListener(v -> showDateTimeDialog(Date_time_in));

        EditText Duration = findViewById(R.id.duration);
//        EditText Name = findViewById(R.id.name);
//        EditText Last_name = findViewById(R.id.last_name);
//        EditText Restouran = findViewById(R.id.restouran);

        Button Reservation = findViewById(R.id.btn_reservate);
        Reservation.setOnClickListener(v -> {

            String people_amount = People_amount.getText().toString();
            String date_time_in = Date_time_in.getText().toString();
            String duration = Duration.getText().toString();
//            String name = Name.getText().toString();
//            String last_name = Last_name.getText().toString();
//            String restouran = Restouran.getText().toString();


            String json = "{\"peopleAmount\":\"" + people_amount + "\", \"reservationTime\":\"" + date_time_in + "\", \"duration\":\"" + duration + "\"}";

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = RESERVATE;
                try {
                    String response = RESTController.sendPost(url, json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Reservation successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReservationForm.this, MainWindow.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect information", Toast.LENGTH_SHORT).show();
                            System.out.println(json);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

    }
    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");

                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(ReservationForm.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        };
        new DatePickerDialog(ReservationForm.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}

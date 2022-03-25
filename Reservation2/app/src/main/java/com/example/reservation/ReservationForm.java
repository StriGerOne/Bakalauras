package com.example.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservationForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        EditText date_time_in=findViewById(R.id.date_time_input);

        date_time_in.setInputType(InputType.TYPE_NULL);

        date_time_in.setOnClickListener(v -> showDateTimeDialog(date_time_in));
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

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(ReservationForm.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        };
        new DatePickerDialog(ReservationForm.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.utils.LocalDateTimeGsonSerializer;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.RESERVATE;

public class ReservationForm extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        //code duplication, not optimal
        final String currentUserId = getIntent().getStringExtra("UserId");
        final String currentUserName = getIntent().getStringExtra("UserName");
        final String currentUserSurname = getIntent().getStringExtra("UserSurname");
        final Long restaurantId = getIntent().getLongExtra("RestaurantId", 0);

        EditText peopleAmountText = findViewById(R.id.amount);
        EditText dateTimeInField = findViewById(R.id.date_time_input); //Java kalboje negalima taip kintamuju vadint, nes yra cammelCaseFormatasIrJoReikiaLaikytis
        dateTimeInField.setInputType(InputType.TYPE_NULL); //Kaip konstanta ok TYPE_NULL
        dateTimeInField.setOnClickListener(v -> showDateTimeDialog(dateTimeInField));

        EditText durationField = findViewById(R.id.duration);
        durationField.setInputType(InputType.TYPE_NULL);
        durationField.setOnClickListener(v -> showTimeDialog(durationField));

        // EditText Name = findViewById(R.id.name); //kodel kintamieji is didziosios raides?? Kaip planuojate nuo klases atskirti?
        EditText firstNameField = findViewById(R.id.name);
        firstNameField.setText(currentUserName);
        EditText lastNameField = findViewById(R.id.last_name);
        lastNameField.setText(currentUserSurname);

        Button Reservation = findViewById(R.id.btn_reservate);
        Reservation.setOnClickListener(v -> {

            String peopleAmount = peopleAmountText.getText().toString();
            String dateTimeIn = dateTimeInField.getText().toString();
            String duration = durationField.getText().toString();

            /** I db turetu ikristi UserID bei RestaurantID,
             * formoje turetu atvaizduoti Name, Las_name, Restaurant**/
//            String name = Name.setText(fname);
//            String last_name = Last_name.getText().toString();
//            String restaurant = Restaurant.getText().toString();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer());
            Properties dataToSend = new Properties();
            dataToSend.setProperty("peopleAmount", peopleAmount);
            dataToSend.setProperty("reservationTime", LocalDateTime.parse(dateTimeIn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString());
            dataToSend.setProperty("duration", duration);
            dataToSend.setProperty("restaurantId", String.valueOf(restaurantId));
            dataToSend.setProperty("userId", currentUserId);

            System.out.println(gsonBuilder.create().toJson(dataToSend, Properties.class));
            if (peopleAmount.isEmpty()) {
                peopleAmountText.setError("People amount is required");
                peopleAmountText.requestFocus();
                return;
            }
            if (dateTimeIn.isEmpty()) {
                dateTimeInField.setError("Starting Date and time is required");
                dateTimeInField.requestFocus();
                return;
            }
            if (duration.isEmpty()) {
                durationField.setError("End time is required");
                durationField.requestFocus();
                return;
            }


            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = RESERVATE;
                try {
                    String response = RESTController.sendPost(url, gsonBuilder.create().toJson(dataToSend, Properties.class));
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Reservation successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReservationForm.this, MainWindow.class);

                            Intent currentIntent = getIntent();
                            String userId = currentIntent.getStringExtra("UserInfo");

                            startActivity(intent);
                            System.out.println(userId);
                        } else {
                            Toast.makeText(getApplicationContext(), "Incorrect information", Toast.LENGTH_SHORT).show();
                            System.out.println(dataToSend);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                GsonBuilder gson = new GsonBuilder();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    gson.registerTypeAdapter(LocalDateTime.class, new DataTimeSerializer());
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(ReservationForm.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        };
        new DatePickerDialog(ReservationForm.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final EditText Duration) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Duration.setText(simpleDateFormat.format(calendar.getTime()));
        };

        new TimePickerDialog(ReservationForm.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

}

package com.example.reservation;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.Serializer.LocalDateTimeGsonSerializer;
import com.example.reservation.utils.SharedPreferenceProvider;
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


        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();
        final String currentUserName =SharedPreferenceProvider.getInstance().getUserName();
        final String currentUserSurname = SharedPreferenceProvider.getInstance().getUserSurname();
        final String currentRestaurant = getIntent().getStringExtra("RestaurantName");
        final Long restaurantId = getIntent().getLongExtra("RestaurantId", 0);


        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());


        EditText peopleAmountText = findViewById(R.id.amount);

        EditText dateTimeInField = findViewById(R.id.dateTime);
        dateTimeInField.setInputType(InputType.TYPE_NULL);
        dateTimeInField.setOnClickListener(v -> showDateTimeDialog(dateTimeInField));

        EditText durationField = findViewById(R.id.duration);
        durationField.setInputType(InputType.TYPE_NULL);
        durationField.setOnClickListener(v -> showTimeDialog(durationField));

        EditText firstNameField = findViewById(R.id.name);
        firstNameField.setEnabled(false);
        firstNameField.setText(currentUserName);

        EditText lastNameField = findViewById(R.id.lastName);
        lastNameField.setEnabled(false);
        lastNameField.setText(currentUserSurname);

        EditText restaurantField = findViewById(R.id.restaurant);
        restaurantField.setEnabled(false);
        restaurantField.setText(currentRestaurant);

        Button Reservation = findViewById(R.id.reservateBtn);
        Reservation.setOnClickListener(v -> {

            String peopleAmount = peopleAmountText.getText().toString();
            String dateTimeIn = dateTimeInField.getText().toString();
            String duration = durationField.getText().toString();


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


            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeGsonSerializer());
            Properties dataToSend = new Properties();
            dataToSend.setProperty("peopleAmount", peopleAmount);
            dataToSend.setProperty("reservationTime", LocalDateTime.parse(dateTimeIn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString());
            dataToSend.setProperty("duration", duration);
            dataToSend.setProperty("restaurantId", String.valueOf(restaurantId));
            dataToSend.setProperty("userId", currentUserId);

            System.out.println(gsonBuilder.create().toJson(dataToSend, Properties.class));

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                String url = RESERVATE;
                try {
                    String response = RESTController.sendPost(url, gsonBuilder.create().toJson(dataToSend, Properties.class));
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Rezervacija atlikta sėkmingai", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReservationForm.this, MainWindow.class);
                            intent.putExtra("UserId", currentUserId);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Neteisinga informacija", Toast.LENGTH_SHORT).show();
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

    private void showTimeDialog(final EditText durationField) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            durationField.setText(simpleDateFormat.format(calendar.getTime()));
        };

        new TimePickerDialog(ReservationForm.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

}

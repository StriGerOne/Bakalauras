package com.example.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Serializer.DataTimeSerializer;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ReservationForm extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

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
                peopleAmountText.setError("Neįveskas žmonių skaičius");
                peopleAmountText.requestFocus();
                return;
            }

               Integer ppl = Integer.parseInt(peopleAmount);
                if (ppl < 1) {
                   peopleAmountText.setError("Žmonių skaičius negali būti mažesnis už 1");
                   peopleAmountText.requestFocus();
                    return;
                }

            if (dateTimeIn.isEmpty()) {
                dateTimeInField.setError("Pasirinkite pradžios datą ir laiką");
                dateTimeInField.requestFocus();
                return;
            }

            Date today = new Date();

            if (duration.isEmpty()) {
                durationField.setError("Pasirinkite numatomą trukmę");
                durationField.requestFocus();
                return;
            }

                Intent intent = new Intent(ReservationForm.this, SelectSeat.class);
                intent.putExtra("RestaurantId", restaurantId);
                intent.putExtra("peopleAmount", peopleAmount);
                intent.putExtra("reservationTime", dateTimeIn);
                intent.putExtra("duration", duration);


                startActivity(intent);
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

                Date time = calendar.getTime();
                Date today = new Date();
                if (today.after(time)) {
                    Toast.makeText(getApplicationContext(), "Neteisingas laikas",Toast.LENGTH_LONG).show();
                }
                else {
                    GsonBuilder gson = new GsonBuilder();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        gson.registerTypeAdapter(LocalDateTime.class, new DataTimeSerializer());
                    }

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                }
            };

            new TimePickerDialog(ReservationForm.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReservationForm.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
  datePickerDialog.show();
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

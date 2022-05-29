package com.example.reservation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.reservation.Controllers.RESTController;
import com.example.reservation.Models.User;
import com.example.reservation.utils.SharedPreferenceProvider;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.reservation.Constants.updateUserInfo;

public class UserEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        final String currentUserId = SharedPreferenceProvider.getInstance().getUserId();
        final String currentUserName = SharedPreferenceProvider.getInstance().getUserName();
        final String currentUserSurname = SharedPreferenceProvider.getInstance().getUserSurname();
        final String currentUserUsername = SharedPreferenceProvider.getInstance().getUserUsername();
        final String currentUserPassword = SharedPreferenceProvider.getInstance().getUserPassword();
        final String currentUserPhone = SharedPreferenceProvider.getInstance().getUserPhone();
        final String currentUserEmail = SharedPreferenceProvider.getInstance().getUserEmail();

        ImageButton backButton = findViewById(R.id.back);
        backButton.setOnClickListener((adapterView) -> finish());

        EditText editName = findViewById(R.id.editFName);
        editName.setText(currentUserName);
        EditText editSurname = findViewById(R.id.editLName);
        editSurname.setText(currentUserSurname);
        EditText editUsername = findViewById(R.id.editUserName);
        editUsername.setText(currentUserUsername);
        EditText editPassword = findViewById(R.id.editPassword);
        editPassword.setText(currentUserPassword);
        EditText editPhone = findViewById(R.id.editPhone);
        editPhone.setText(currentUserPhone);
        EditText editEmail = findViewById(R.id.editEmail);
        editEmail.setText(currentUserEmail);

        Button change = findViewById(R.id.changeBtn);
        change.setOnClickListener(v -> {

            String firstname = editName.getText().toString();
            String lastname = editSurname.getText().toString();
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            String phone = editPhone.getText().toString();
            String email = editEmail.getText().toString();

            String json = "{\"fname\":\"" + firstname + "\", \"lname\":\"" + lastname + "\", \"username\":\"" + username
                    + "\", \"password\":\"" + password + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\"}";

            if (firstname.isEmpty()) {
                editName.setError("Vardo laukas yra privalomas");
                editName.requestFocus();
                return;
            }
            if (!firstname.matches("[a-zA-Z ]+")) {
                editName.requestFocus();
                editName.setError("Neteisingas vardo formatas");
                return;
            }
            if (lastname.isEmpty()) {
                editSurname.setError("Pavardės laukas yra privalomas");
                editSurname.requestFocus();
                return;
            }
            if (!firstname.matches("[a-zA-Z ]+")) {
                editSurname.requestFocus();
                editSurname.setError("Neteisingas pavardės formatas");
                return;
            }
            if (username.isEmpty()) {
                editUsername.setError("Vartotojo vardo laukas yra privalomas");
                editUsername.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editPassword.setError("Slaptažodžio laukas yra privalomas");
                editPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                editPassword.setError("Slaptažodis turi būti bent iš 6 simbolių");
                editPassword.requestFocus();
                return;
            }
            if (phone.isEmpty()) {
                editPhone.setError("Telefono numerio laukas yra privalomas");
                editPhone.requestFocus();
                return;
            }
            if (email.isEmpty()) {
                editEmail.setError("El. pašto laukas yra privalomas");
                editEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.setError("Netinkamas el.pašto formatas");
                editEmail.requestFocus();
                return;
            }



            SharedPreferenceProvider sharedPreferences = SharedPreferenceProvider.getInstance();

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                try {
                    String response = RESTController.sendPut(updateUserInfo(Long.valueOf(currentUserId)), json);
                    handler.post(() -> {
                        if (!response.equals("Error") && !response.equals("")) {
                            Toast.makeText(getApplicationContext(), "Informacija atnaujinta", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            User user = gson.fromJson(response, User.class);
                            sharedPreferences.saveValue("UserId",user.getId());
                            sharedPreferences.saveValue("UserName",user.getFname());
                            sharedPreferences.saveValue("UserSurname",user.getLname());
                            sharedPreferences.saveValue("UserUsername",user.getUsername());
                            sharedPreferences.saveValue("UserPassword",user.getPassword());
                            sharedPreferences.saveValue("UserPhone",user.getPhone());
                            sharedPreferences.saveValue("UserEmail",user.getEmail());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bloga informacija", Toast.LENGTH_SHORT).show();
                            System.out.println(json);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
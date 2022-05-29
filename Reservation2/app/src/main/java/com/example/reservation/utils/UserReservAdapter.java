package com.example.reservation.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.reservation.Models.Rating;
import com.example.reservation.Models.Reservation;
import com.example.reservation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserReservAdapter extends CustomListAdapter {


    public UserReservAdapter(Activity activity, List<Reservation> listElement) {
        super(activity, listElement);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_4, null);
        TextView restaurantName = convertView.findViewById(R.id.restName);
        TextView date = convertView.findViewById(R.id.date);
        TextView reserPeopleAmount = convertView.findViewById(R.id.pplAmount);
        TextView reservDate = convertView.findViewById(R.id.reservDate);
        TextView status = convertView.findViewById(R.id.status);

        Reservation r = (Reservation) listElement.get(position);

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String format = dateFormat.format(parser.parse(r.getToday().toString()));
            date.setText("Atlikta: " + format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String format = dateFormat1.format(parser1.parse(r.getReservationTime().toString()));
            reservDate.setText("Nuo: " + format + " Iki: " + r.getDuration());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        restaurantName.setText("Restoranas: " + r.getRestaurantName());
        reserPeopleAmount.setText("Žmonių skaičius: " + r.getPeopleAmount());
        status.setText("Statusas: " + r.getStatus());

        return convertView;
    }
}

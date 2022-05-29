package com.example.reservation.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.reservation.Models.Rating;
import com.example.reservation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserRatingAdapter extends CustomListAdapter {


    public UserRatingAdapter(Activity activity, List<Rating> listElement) {
        super(activity, listElement);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_3, null);
        TextView restaurantName = convertView.findViewById(R.id.restName);
        TextView commentText = convertView.findViewById(R.id.commentText);
        TextView rate = convertView.findViewById(R.id.rate);
        TextView date = convertView.findViewById(R.id.rateDate);

        Rating r = (Rating) listElement.get(position);

        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String format = dateFormat.format(parser.parse(r.getRateTime().toString()));
            date.setText(format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        restaurantName.setText("Restoranas: " + r.getRestName());
        commentText.setText(r.getComment());
        rate.setText("Įvertinimas: " + r.getRating());

        return convertView;
    }
}
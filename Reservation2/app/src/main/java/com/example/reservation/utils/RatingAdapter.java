package com.example.reservation.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.reservation.Models.Rating;
import com.example.reservation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RatingAdapter extends CustomListAdapter {


    public RatingAdapter(Activity activity, List<Rating> listElement) {
        super(activity, listElement);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_2, null);
        TextView username = convertView.findViewById(R.id.commentAuthor);
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

        username.setText("Vartotojas: " + r.getUsername());
        commentText.setText(r.getComment());
        rate.setText(String.valueOf("Įvertinimas: " + r.getRating()));


        return convertView;
    }
}
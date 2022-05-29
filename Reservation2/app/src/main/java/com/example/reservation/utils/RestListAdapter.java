package com.example.reservation.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.reservation.Models.Rating;
import com.example.reservation.R;

import java.util.List;

public class RestListAdapter extends CustomListAdapter {


    public RestListAdapter(Activity activity, List<Rating> listElement) {
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
        commentText.setText(r.getComment());
        rate.setText(String.valueOf(r.getRating()));
        date.setText(String.valueOf(r.getRateTime()));

        return convertView;
    }
}

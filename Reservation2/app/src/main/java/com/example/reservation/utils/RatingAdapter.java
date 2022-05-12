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
        TextView commentTitle = convertView.findViewById(R.id.commentTitle);
        TextView commentText = convertView.findViewById(R.id.commentText);
        TextView username = convertView.findViewById(R.id.username);
        Rating r = (Rating) listElement.get(position);
        commentTitle.setText(r.getComment());
        commentText.setText(r.getComment());
        //ka reikia cia prisiskirkit
        return convertView;
    }
}

package com.example.reservation.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.reservation.Models.Rating;
import com.example.reservation.Models.Restaurant;
import com.example.reservation.R;

import java.util.List;

import static com.example.reservation.Constants.ADRESS;

public class CustomListAdapter extends BaseAdapter {
    protected Activity activity;
    protected LayoutInflater inflater;
    protected List listElement;


    public CustomListAdapter(Activity activity, List listElement) {
        this.activity = activity;
        this.listElement = listElement;
    }

    @Override
    public int getCount() {
        return listElement.size();
    }

    @Override
    public Object getItem(int location) {
        return listElement.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        return convertView;
    }
}

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
import com.example.reservation.Models.Restaurant;
import com.example.reservation.R;

import java.util.List;

import static com.example.reservation.Constants.ADRESS;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Restaurant> restaurantList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Restaurant> restaurantList) {
        this.activity = activity;
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int location) {
        return restaurantList.get(location);
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

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = convertView
                .findViewById(R.id.thumbnail);
        TextView title = convertView.findViewById(R.id.title);
        TextView phoneNum = convertView.findViewById(R.id.phoneNum);
        TextView address = convertView.findViewById(R.id.address);
        Restaurant m = restaurantList.get(position);
        thumbNail.setImageUrl(ADRESS + "images/" + m.getImageUrl(), imageLoader);
        title.setText(m.getRestaurantName());
        phoneNum.setText("Telefono numeris: " + m.getPhone());
        address.setText(String.valueOf(m.getAddress()));

        return convertView;
    }
}
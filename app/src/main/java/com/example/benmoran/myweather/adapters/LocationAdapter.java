package com.example.benmoran.myweather.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.benmoran.myweather.R;

/**
 * Created by Ben Moran on 14/06/16.
 */
public class LocationAdapter extends BaseAdapter {

    private String[] locations;
    private String currentLocation;
    private LayoutInflater layoutInflater;

    public LocationAdapter(String[] locations, LayoutInflater layoutInflater) {
        this.locations = locations;
        this.layoutInflater = layoutInflater;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public int getCount() {
        return locations.length;
    }

    @Override
    public String getItem(int position) {
        return locations[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.location_name, null);
        }

        String locationName = locations[position];
        if(getCurrentLocation().equals(locationName)) {
            convertView.setBackgroundColor(Color.parseColor("#03A9F4"));
        }else{
            convertView.setBackground(null);
        }

        TextView lblName = (TextView) convertView.findViewById(R.id.lbl_location_name);
        lblName.setText(locationName);
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return !locations[position].equals(currentLocation);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}

package com.mapstest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mapstest.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapsInfoAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    LayoutInflater inflater;
    public MapsInfoAdapter(Context context) {
        this.context = context;
    }
    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        inflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // R.layout.echo_info_window is a layout in my
        // res/layout folder. You can provide your own
        View v = inflater.inflate(R.layout.maps_info, null);

        TextView title = (TextView) v.findViewById(R.id.artistName);
        TextView subtitle = (TextView) v.findViewById(R.id.artistInfo);
        title.setText(marker.getTitle());
        subtitle.setText(marker.getSnippet());
        return v;
    }
}
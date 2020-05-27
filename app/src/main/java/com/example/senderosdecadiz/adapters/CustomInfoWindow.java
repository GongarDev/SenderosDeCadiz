package com.example.senderosdecadiz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.senderosdecadiz.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

//Esta clase sirve para construir el layout de información que aparece en el marcador de googleMaps.

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private View view;
    private Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
        view= LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView title= view.findViewById(R.id.window_info_title);
        title.setText(marker.getTitle());

        TextView description= view.findViewById(R.id.window_info_desc);
        description.setText(marker.getSnippet());

        return view;
    }
}

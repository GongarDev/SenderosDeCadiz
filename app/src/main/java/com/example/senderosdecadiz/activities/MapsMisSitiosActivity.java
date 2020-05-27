package com.example.senderosdecadiz.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.adapters.CustomInfoWindow;
import com.example.senderosdecadiz.models.Sitio;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsMisSitiosActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Sitio sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_sitios);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (intent != null) {
            sitio = (Sitio) intent.getSerializableExtra("sitio");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));

        if (sitio != null) {

            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(sitio.getLatitude(), sitio.getLongitude())).
                    title(String.valueOf(sitio.getName())).
                    snippet(sitio.getDescription()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(sitio.getLatitude(), sitio.getLongitude())));
            mMap.setMinZoomPreference(10);
        }
    }
}

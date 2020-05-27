package com.example.senderosdecadiz.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.adapters.CustomInfoWindow;
import com.example.senderosdecadiz.models.Sendero;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsSenderosActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private Sendero sendero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_senderos);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent= getIntent();
        if (intent!=null){
            sendero = (Sendero) intent.getSerializableExtra("sendero");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);

        if (sendero!=null){

            mMap.addMarker(new MarkerOptions().
                    position(new LatLng(sendero.getLatitude(),sendero.getLongitude())).
                    title(String.valueOf(sendero.getNombre())).
                    snippet("Recorrido: " + sendero.getDistancia() + " metros"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(sendero.getLatitude(),sendero.getLongitude())));
            mMap.setMinZoomPreference(10);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(sendero.getInfo()));
        startActivity(intent);
    }
}

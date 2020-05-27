package com.example.senderosdecadiz.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.adapters.SitioAdapter;
import com.example.senderosdecadiz.models.Sitio;
import com.example.senderosdecadiz.viewmodels.MisSitiosViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MisSitiosFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SitioAdapter adapter;
    private List<Sitio> sitiosList = new ArrayList<>();
    private MisSitiosViewModel misSitiosViewModel;
    private TextView emptyView;
    private OnSitioSelected callback;

    private int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude;
    private double longitude;

    public MisSitiosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());

        getLastLocation();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        misSitiosViewModel =
                ViewModelProviders.of(this).get(MisSitiosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mis_sitios, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewSitios);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        emptyView = root.findViewById(R.id.NoFoundSiteTextView);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPopUp();

            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        final ProgressBar progressBar;
        progressBar = root.findViewById(R.id.progressBarMisSitios);

        if (isConnected) {

            MisSitiosViewModel model = ViewModelProviders.of(this).get(MisSitiosViewModel.class);

            model.getSitios().observe(this, new Observer<List<Sitio>>() {
                @Override
                public void onChanged(List<Sitio> sitios) {

                    progressBar.setVisibility(View.GONE);

                    if (sitios != null) {
                        sitiosList = sitios;

                        adapter = new SitioAdapter(sitiosList, R.layout.list_item_sitios, getActivity(), new SitioAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Sitio sitio, int position) {
                                callback.OnSitioSelected(sitio);
                            }
                        }, new SitioAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Sitio sitio, int position) {
                                editSitio(sitio);
                            }
                        }, new SitioAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Sitio sitio, int position) {
                                deleteSitio(sitio);
                            }
                        });

                        recyclerView.setAdapter(adapter);
                    }

                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
        }

        return root;
    }

    private void createPopUp() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_sitios_insert_edit, null);

        builder.setView(view);

        dialog = builder.create();
        dialog.show();

        final EditText sitioName = view.findViewById(R.id.popupSitioName);
        final EditText sitioDescription = view.findViewById(R.id.popupSitioDescription);
        final EditText sitioLocalidad= view.findViewById(R.id.popupSitioLocalidad);
        Button saveButton = view.findViewById(R.id.popupSaveSitioButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(sitioName.getText()) && !TextUtils.isEmpty(sitioDescription.getText())) {

                    saveSitioDb(sitioName.getText().toString(), sitioLocalidad.getText().toString(), sitioDescription.getText().toString());
                }
            }
        });
    }

    private void saveSitioDb(String name, String localidad, String description) {

        Sitio sitio= new Sitio(name, this.latitude, this.longitude, localidad, description);

        misSitiosViewModel.addSitio(sitio);

        dialog.dismiss();
    }

    private void editSitio(final Sitio sitio) {

        builder = new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.popup_sitios_insert_edit, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText sitioName= view.findViewById(R.id.popupSitioName);
        final EditText sitioDescription= view.findViewById(R.id.popupSitioDescription);
        final EditText sitioLocalidad= view.findViewById(R.id.popupSitioLocalidad);
        final Button saveButton= view.findViewById(R.id.popupSaveSitioButton);

        sitioName.setText(sitio.getName());
        sitioDescription.setText(sitio.getDescription());
        sitioLocalidad.setText(sitio.getLocalidad());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(sitioName.getText()) && !TextUtils.isEmpty(sitioDescription.getText())){

                    sitio.setName(sitioName.getText().toString());
                    sitio.setDescription(sitioDescription.getText().toString());
                    sitio.setLocalidad(sitioLocalidad.getText().toString());
                    misSitiosViewModel.updateSitio(sitio);
                }

                dialog.dismiss();
            }
        });
    }

    private void deleteSitio(Sitio sitio) {
        misSitiosViewModel.deleteSitio(sitio);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (MisSitiosFragment.OnSitioSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnSitioSelected");
        }
    }

    public interface OnSitioSelected {
        public void OnSitioSelected(Sitio sitio);
    }


    //To este código sirve para coger automáticamente las coordendas del lugar actual.

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this.getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this.getContext(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}
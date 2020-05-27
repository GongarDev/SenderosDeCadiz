package com.example.senderosdecadiz.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.activities.MapsSenderosActivity;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.models.Tiempo;
import com.example.senderosdecadiz.utils.QueryTiempo;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetallesSenderoFragment extends Fragment {

    LinearLayout containerDetalles;
    TextView textViewNombre;
    TextView textViewDistancia;
    TextView textViewDuracion;
    TextView textViewDificultad;
    TextView textViewTipo;
    TextView textViewPermiso;
    TextView textViewSenializado;
    TextView textViewCotaMax;
    TextView textViewCotaMin;
    ImageView imageViewmap;

    ImageView imageViewWeatherIcon;
    TextView textViewWeatherDescrip;
    TextView textViewWeatherTemp;
    TextView textViewWeatherWind;
    TextView textViewWeatherHum;
    TextView textViewWeatherSunset;

    public DetallesSenderoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalles_sendero, container, false);

        containerDetalles = view.findViewById(R.id.containerDetalles);
        textViewNombre = view.findViewById(R.id.textViewNombre);
        textViewDistancia = view.findViewById(R.id.textViewDistancia);
        textViewDuracion = view.findViewById(R.id.textViewDuracion);
        textViewDificultad = view.findViewById(R.id.textViewDificultad);
        textViewTipo = view.findViewById(R.id.textViewTipo);
        textViewPermiso = view.findViewById(R.id.textViewPermiso);
        textViewSenializado = view.findViewById(R.id.textViewSenializado);
        textViewCotaMax = view.findViewById(R.id.textViewCotaMax);
        textViewCotaMin = view.findViewById(R.id.textViewCotaMin);
        imageViewmap = view.findViewById(R.id.imageViewMap);
        imageViewWeatherIcon = view.findViewById(R.id.imageViewWeatherIcon);
        textViewWeatherDescrip = view.findViewById(R.id.textViewWeatherDescrip);
        textViewWeatherTemp = view.findViewById(R.id.textViewWeatherTemp);
        textViewWeatherWind = view.findViewById(R.id.textViewWeatherWind);
        textViewWeatherHum = view.findViewById(R.id.textViewWeatherHum);
        textViewWeatherSunset = view.findViewById(R.id.textViewWeatherSunset);
        return view;
    }

    public void showSendero(final Sendero sendero) {

        textViewNombre.setText(sendero.getNombre());
        textViewDistancia.setText(sendero.getDistancia() + " metros");
        textViewDuracion.setText(sendero.getDuracion());
        textViewDificultad.setText(sendero.getDificultad());
        textViewTipo.setText(sendero.getTipo());
        textViewPermiso.setText(sendero.getPermiso());
        textViewSenializado.setText(sendero.getSenializado());
        textViewCotaMax.setText(sendero.getCotamax() + " metros");
        textViewCotaMin.setText(sendero.getCotamin() + " metros");

        imageViewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MapsSenderosActivity.class);
                intent.putExtra("sendero", sendero);
                startActivity(intent);
            }
        });

        loadWeather(sendero);
    }

    private void loadWeather(Sendero sendero) {

        final Tiempo[] tiempo = {null};
        String REQUEST_URL = "http://api.openweathermap.org/data/2.5/weather?lat="+sendero.getLatitude()
                +"&lon="+sendero.getLongitude()+"&units=metric&lang=es&appid=5ac1962bec5c164a667eeed33f0c0268";
        Uri baseUri= Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder= baseUri.buildUpon();

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest request= new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                tiempo[0] = QueryTiempo.extractFeatureFromJson(response);
                Picasso.get().load(tiempo[0].getIcon()).fit().into(imageViewWeatherIcon);
                textViewWeatherDescrip.setText(tiempo[0].getDescription());
                textViewWeatherTemp.setText(String.valueOf(tiempo[0].getTemp()) + "º");

                DecimalFormat formatter = new DecimalFormat("#0.00");
                textViewWeatherWind.setText(formatter.format(tiempo[0].getWind()*3.6) + " km/h");
                textViewWeatherHum.setText(tiempo[0].getHumidity() + "%");
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(new Date(tiempo[0].getSunset()));
                textViewWeatherSunset.setText(time + " horas");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error Volley", error.toString());

            }
        });
        requestQueue.add(request);
    }
}

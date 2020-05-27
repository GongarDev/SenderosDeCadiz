package com.example.senderosdecadiz.viewmodels;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.utils.QuerySenderos;


import java.util.List;

public class SenderosViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Sendero>> senderosList;
    private Application application = getApplication();
    private static final String REQUEST_URL = "https://apirtod.dipucadiz.es/api/datos/senderos.json";

    public SenderosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Sendero>> getSenderosList() {

        if (senderosList==null){
            senderosList= new MutableLiveData<>();
            loadSenderos();
        }
        return senderosList;
    }

    private void loadSenderos() {

        Uri baseUri= Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder= baseUri.buildUpon();

        RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request= new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                List<Sendero> senderos= QuerySenderos.extractFeatureFromJson(response);

                senderosList.setValue(senderos);

                Log.d("Response: ", response);

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
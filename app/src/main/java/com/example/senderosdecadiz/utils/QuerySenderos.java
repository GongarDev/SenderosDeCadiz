package com.example.senderosdecadiz.utils;

import android.text.TextUtils;
import android.util.Log;
import com.example.senderosdecadiz.models.Sendero;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class QuerySenderos {

    private QuerySenderos() { }

    public static List<Sendero> extractFeatureFromJson(String senderoJSON) {

        if (TextUtils.isEmpty(senderoJSON)) {
            return null;
        }

        List<Sendero> senderos = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(senderoJSON);

            JSONArray senderoArray = baseJsonResponse.getJSONArray("resources");

            for (int i = 0; i < senderoArray.length(); i++) {

                JSONObject currentSendero = senderoArray.getJSONObject(i);

                String municipio = currentSendero.getString("ca:municipio");

                String nombre = null;

                try {
                    nombre = new String(currentSendero.getString("ca:nombre").getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                double longitud = currentSendero.getDouble("ca:coord_longitud");

                double latitud = currentSendero.getDouble("ca:coord_latitud");

                String uri = currentSendero.getString("uri");

                int distancia = currentSendero.getInt("ca:distancia");

                String dificultad = currentSendero.getString("ca:dificultad");

                String duracion = currentSendero.getString("ca:duracion");

                String tipo = currentSendero.getString("ca:tipo");

                String permiso = currentSendero.getString("ca:permiso");

                String senializado = currentSendero.getString("ca:senal");

                int cotamax = currentSendero.getInt("ca:cotamax");

                int cotamin = currentSendero.getInt("ca:cotamin");

                String info = currentSendero.getString("ca:info");

                Sendero sendero = new Sendero(municipio, nombre, longitud, latitud, uri, distancia,
                        dificultad, duracion, tipo, permiso, senializado, cotamax, cotamin, info);

                senderos.add(sendero);
            }

        } catch (JSONException e) {
            Log.e("QuerySenderos", "Hubo un problema parseando los resultados de senderos JSON", e);
        }

        return senderos;
    }
}

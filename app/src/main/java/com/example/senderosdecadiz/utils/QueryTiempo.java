package com.example.senderosdecadiz.utils;

import android.text.TextUtils;
import android.util.Log;
import com.example.senderosdecadiz.models.Tiempo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueryTiempo {

    private QueryTiempo() { }

    public static Tiempo extractFeatureFromJson(String tiempoJSON) {

        if (TextUtils.isEmpty(tiempoJSON)) {
            return null;
        }

        Tiempo tiempo = null;

        try {

            JSONObject baseJsonResponse = new JSONObject(tiempoJSON);

            JSONArray tiempoArray = baseJsonResponse.getJSONArray("weather");

            JSONObject currentWeather = tiempoArray.getJSONObject(0);

            String icon = currentWeather.getString("icon");

            String description = currentWeather.getString("description");

            JSONObject main = baseJsonResponse.getJSONObject("main");

            double temp = main.getDouble("temp");

            int humidity = main.getInt("humidity");

            JSONObject wind = baseJsonResponse.getJSONObject("wind");

            double windSpeed = wind.getDouble("speed");

            JSONObject sys = baseJsonResponse.getJSONObject("sys");

            long sunset = sys.getLong("sunset");

            tiempo = new Tiempo(icon, description, temp, windSpeed, humidity, sunset);

        } catch (JSONException e) {
            Log.e("QuerySenderos", "Hubo un problema parseando los resultados de senderos JSON", e);
        }

        return tiempo;
    }
}

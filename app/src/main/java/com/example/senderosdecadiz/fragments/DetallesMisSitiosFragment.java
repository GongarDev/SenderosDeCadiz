package com.example.senderosdecadiz.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.activities.MapsMisSitiosActivity;
import com.example.senderosdecadiz.adapters.ImagenAdapter;
import com.example.senderosdecadiz.models.Imagen;
import com.example.senderosdecadiz.models.Sitio;
import com.example.senderosdecadiz.models.Tiempo;
import com.example.senderosdecadiz.utils.QueryTiempo;
import com.example.senderosdecadiz.viewmodels.GaleriaFotosViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DetallesMisSitiosFragment extends Fragment {

    LinearLayout containerDetalles;
    TextView textViewNombre;
    TextView textViewDescripcion;
    ImageView imageViewmap;

    private RecyclerView recyclerView;
    private ImagenAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GaleriaFotosViewModel galeriaFotosViewModel;
    private OnImagenSelected callback;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String currentPhotoPath;
    private long idSitio;

    ImageView imageViewWeatherIcon;
    TextView textViewWeatherDescrip;
    TextView textViewWeatherTemp;
    TextView textViewWeatherWind;
    TextView textViewWeatherHum;
    TextView textViewWeatherSunset;

    public DetallesMisSitiosFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalles_sitio, container, false);

        containerDetalles = view.findViewById(R.id.containerDetallesMisSitios);
        textViewNombre = view.findViewById(R.id.textViewName);
        textViewDescripcion = view.findViewById(R.id.textViewDescripcion);

        recyclerView = view.findViewById(R.id.recyclerViewGaleriaFotos);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takePhoto();
            }
        });

        galeriaFotosViewModel =
                ViewModelProviders.of(this).get(GaleriaFotosViewModel.class);

        galeriaFotosViewModel.getImagenesList().observe(this, new Observer<List<Imagen>>() {
            @Override
            public void onChanged(List<Imagen> imagenes) {

                if (imagenes != null) {

                    adapter = new ImagenAdapter(idSitio, imagenes, R.layout.list_item_imagenes, getActivity(), new ImagenAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Imagen imagen, int position) {
                            callback.OnImagenSelected(imagen);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        imageViewmap = view.findViewById(R.id.imageViewMap);
        imageViewWeatherIcon = view.findViewById(R.id.imageViewWeatherIcon);
        textViewWeatherDescrip = view.findViewById(R.id.textViewWeatherDescrip);
        textViewWeatherTemp = view.findViewById(R.id.textViewWeatherTemp);
        textViewWeatherWind = view.findViewById(R.id.textViewWeatherWind);
        textViewWeatherHum = view.findViewById(R.id.textViewWeatherHum);
        textViewWeatherSunset = view.findViewById(R.id.textViewWeatherSunset);

        return view;
    }

    public void showSitio(final Sitio sitio) {

        idSitio = sitio.getId();
        textViewNombre.setText(sitio.getName());
        textViewDescripcion.setText(sitio.getDescription());

        imageViewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsMisSitiosActivity.class);
                intent.putExtra("sitio", sitio);
                startActivity(intent);
            }
        });
        loadWeather(sitio);
    }

    private void loadWeather(Sitio sitio) {

        final Tiempo[] tiempo = {null};
        String REQUEST_URL = "http://api.openweathermap.org/data/2.5/weather?lat=" + sitio.getLatitude()
                + "&lon=" + sitio.getLongitude() + "&units=metric&lang=es&appid=5ac1962bec5c164a667eeed33f0c0268";
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, uriBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                tiempo[0] = QueryTiempo.extractFeatureFromJson(response);
                Picasso.get().load(tiempo[0].getIcon()).fit().into(imageViewWeatherIcon);
                textViewWeatherDescrip.setText(tiempo[0].getDescription());
                textViewWeatherTemp.setText(tiempo[0].getTemp() + "º");

                DecimalFormat formatter = new DecimalFormat("#0.00");
                textViewWeatherWind.setText(formatter.format(tiempo[0].getWind() * 3.6) + " km/h");
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

    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                System.out.println("Error al abrir la cámara.");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.senderosdecadiz.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            saveImagenDb(currentPhotoPath, idSitio);
            adapter.notifyDataSetChanged();
        }
    }

    private void saveImagenDb(String currentPhotoPath, long id_fk) {

        Imagen imagen= new Imagen(currentPhotoPath, id_fk);

        galeriaFotosViewModel.addImagen(imagen);
    }


    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnImagenSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnImagenSelected");
        }
    }
    public interface OnImagenSelected {
        public void OnImagenSelected(Imagen imagen);
    }
}

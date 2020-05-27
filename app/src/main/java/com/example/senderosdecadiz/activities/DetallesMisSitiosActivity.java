package com.example.senderosdecadiz.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.fragments.DetallesMisSitiosFragment;
import com.example.senderosdecadiz.models.Imagen;
import com.example.senderosdecadiz.models.Sitio;

import java.io.File;

public class DetallesMisSitiosActivity extends AppCompatActivity implements DetallesMisSitiosFragment.OnImagenSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_sitio);

        Intent intent = getIntent();

        if(intent != null) {

            Sitio sitio = (Sitio) intent.getSerializableExtra("sitio");

            DetallesMisSitiosFragment detallesMisSitiosFragment = (DetallesMisSitiosFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileDetallesMisSitios);

            detallesMisSitiosFragment.showSitio(sitio);
        }
    }

    @Override
    public void OnImagenSelected(Imagen imagen) {

        File imgFile = new File(imagen.getPath());
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);

        Uri uri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext()
                .getPackageName() + ".fileprovider", imgFile);

        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri,"image/*");
        getApplicationContext().startActivity(intent);
    }
}

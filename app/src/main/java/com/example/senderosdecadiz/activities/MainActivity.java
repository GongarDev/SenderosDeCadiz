package com.example.senderosdecadiz.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.fragments.DetallesMisSitiosFragment;
import com.example.senderosdecadiz.fragments.DetallesSenderoFragment;
import com.example.senderosdecadiz.fragments.FavoritosFragment;
import com.example.senderosdecadiz.fragments.MisSitiosFragment;
import com.example.senderosdecadiz.fragments.SenderosFragment;
import com.example.senderosdecadiz.models.Imagen;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.models.Sitio;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SenderosFragment.OnSenderoSelected,
        MisSitiosFragment.OnSitioSelected, FavoritosFragment.OnSenderoFavortioSelected, DetallesMisSitiosFragment.OnImagenSelected  {

    LinearLayout contenedorDetallesVacio;
    LinearLayout contenedorDetallesSendero;
    LinearLayout contenedorDetallesSitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_senderos, R.id.navigation_mis_sitios, R.id.navigation_favoritos)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        contenedorDetallesVacio = findViewById(R.id.container_fragmentTabletDetallesVacio);
        contenedorDetallesSendero = findViewById(R.id.container_fragmentTabletDetallesSendero);
        contenedorDetallesSitio = findViewById(R.id.container_fragmentTabletDetallesSitio);
    }

    @Override
    public void OnSenderoSelected(Sendero sendero) {

        if (contenedorDetallesSendero != null) {
            contenedorDetallesVacio.setVisibility(View.GONE);
            contenedorDetallesSitio.setVisibility(View.GONE);
            contenedorDetallesSendero.setVisibility(View.VISIBLE);
        }

        DetallesSenderoFragment detallesSenderoFragment =
                (DetallesSenderoFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragmentTabletDetallesSendero);

        if (detallesSenderoFragment != null) {
            detallesSenderoFragment.showSendero(sendero);

        } else {
            Intent intent = new Intent(this, DetallesSenderoActivity.class);
            intent.putExtra("sendero", sendero);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public void OnSitioSelected(Sitio sitio) {

        if (contenedorDetallesSendero != null) {
            contenedorDetallesVacio.setVisibility(View.GONE);
            contenedorDetallesSendero.setVisibility(View.GONE);
            contenedorDetallesSitio.setVisibility(View.VISIBLE);
        }

        DetallesMisSitiosFragment detallesMisSitiosFragment =
                (DetallesMisSitiosFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragmentTabletDetallesSitio);

        if (detallesMisSitiosFragment != null) {
            detallesMisSitiosFragment.showSitio(sitio);
        } else {
            Intent intent = new Intent(this, DetallesMisSitiosActivity.class);
            intent.putExtra("sitio", sitio);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public void OnSenderoFavortioSelected(Sendero sendero) {

        if (contenedorDetallesSendero != null) {
            contenedorDetallesVacio.setVisibility(View.GONE);
            contenedorDetallesSitio.setVisibility(View.GONE);
            contenedorDetallesSendero.setVisibility(View.VISIBLE);
        }

        DetallesSenderoFragment detallesSenderoFragment =
                (DetallesSenderoFragment) getSupportFragmentManager().
                        findFragmentById(R.id.fragmentTabletDetallesSendero);

        if (detallesSenderoFragment != null) {
            detallesSenderoFragment.showSendero(sendero);

        } else {
            Intent intent = new Intent(this, DetallesSenderoActivity.class);
            intent.putExtra("sendero", sendero);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
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

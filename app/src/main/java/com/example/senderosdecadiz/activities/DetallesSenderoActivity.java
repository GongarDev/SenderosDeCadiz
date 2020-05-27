package com.example.senderosdecadiz.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.fragments.DetallesSenderoFragment;
import com.example.senderosdecadiz.models.Sendero;

public class DetallesSenderoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_sendero);

        Intent intent = getIntent();

        if(intent != null) {

            Sendero sendero = (Sendero) intent.getSerializableExtra("sendero");

            DetallesSenderoFragment detallesSenderoFragment = (DetallesSenderoFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileDetalles);

            detallesSenderoFragment.showSendero(sendero);
        }
    }
}

package com.example.senderosdecadiz.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "sitio")
public class Sitio implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    private double latitude;
    private double longitude;

    private String localidad;

    private String description;

    @Ignore
    public Sitio() {
    }

    public Sitio(long id, String name, double latitude, double longitude,String localidad, String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localidad = localidad;
        this.description = description;
    }

    @Ignore
    public Sitio(String name, double latitude, double longitude, String localidad, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localidad = localidad;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

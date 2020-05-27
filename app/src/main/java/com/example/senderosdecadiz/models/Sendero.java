package com.example.senderosdecadiz.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "sendero")
public class Sendero implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String municipio;
    private String nombre;
    private double longitude;
    private double latitude;
    private String uri;
    private int distancia;
    private String dificultad;
    private String duracion;
    private String tipo;
    private String permiso;
    private String senializado;
    private int cotamax;
    private int cotamin;
    private String info;
    private int realizado;
    @Ignore
    private boolean favorito;

    @Ignore
    public Sendero(String municipio, String nombre, double longitude, double latitude, String uri,
                   int distancia, String dificultad, String duracion, String tipo, String permiso,
                   String senializado, int cotamax, int cotamin, String info) {

        this.municipio = municipio;
        this.nombre = nombre;
        this.longitude = longitude;
        this.latitude = latitude;
        this.uri = uri;
        this.distancia = distancia;
        this.dificultad = dificultad;
        this.duracion = duracion;

        if(tipo.equals("C"))
            this.tipo = "Circular";
        else if (tipo.equals("L"))
            this.tipo = "Lineal";
        else if (tipo.equals("S"))
            this.tipo = "Semi-circular";
        else
            this.tipo = tipo;

        this.permiso = permiso;
        this.senializado = senializado;
        this.cotamax = cotamax;
        this.cotamin = cotamin;
        this.info = info;
        this.realizado = 0;
        this.favorito = false;
    }

    public Sendero(long id, String municipio, String nombre, double longitude, double latitude, String uri,
                   int distancia, String dificultad, String duracion, String tipo, String permiso,
                   String senializado, int cotamax, int cotamin, String info, int realizado) {

        this.id = id;
        this.municipio = municipio;
        this.nombre = nombre;
        this.longitude = longitude;
        this.latitude = latitude;
        this.uri = uri;
        this.distancia = distancia;
        this.dificultad = dificultad;
        this.duracion = duracion;

        if(tipo.equals("C"))
            this.tipo = "Circular";
        else if (tipo.equals("L"))
            this.tipo = "Lineal";
        else if (tipo.equals("S"))
            this.tipo = "Semi-circular";
        else
            this.tipo = tipo;

        this.permiso = permiso;
        this.senializado = senializado;
        this.cotamax = cotamax;
        this.cotamin = cotamin;
        this.info = info;
        this.realizado = realizado;
        this.favorito = false;
    }

    @Ignore
    public Sendero(String municipio, String nombre, double longitude, double latitude) {
        this.municipio = municipio;
        this.nombre = nombre;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getSenializado() {
        return senializado;
    }

    public void setSenializado(String senializado) {
        this.senializado = senializado;
    }

    public int getCotamax() {
        return cotamax;
    }

    public void setCotamax(int cotamax) {
        this.cotamax = cotamax;
    }

    public int getCotamin() {
        return cotamin;
    }

    public void setCotamin(int cotamin) {
        this.cotamin = cotamin;
    }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public int getRealizado() {
        return realizado;
    }

    public void setRealizado(int realizado) {
        this.realizado = realizado;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}

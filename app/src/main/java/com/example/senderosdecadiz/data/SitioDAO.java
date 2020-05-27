package com.example.senderosdecadiz.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.senderosdecadiz.models.Sitio;

import java.util.List;

@Dao
public interface SitioDAO {

    @Insert
    public long insertSitio(Sitio sitio);

    @Update
    public int updateSitio(Sitio sitio);

    @Delete
    public int deleteSitio(Sitio sitio);

    @Query("SELECT * FROM SITIO WHERE id=:id")
    public Sitio getSitio(long id);

    @Query("SELECT * FROM SITIO")
    public LiveData<List<Sitio>> getSitios();
}

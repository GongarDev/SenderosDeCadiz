package com.example.senderosdecadiz.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.senderosdecadiz.models.Sendero;

import java.util.List;

@Dao
public interface FavoritoDAO {

    @Insert
    public long insertSendero(Sendero sendero);

    @Delete
    public int deleteSendero(Sendero sendero);

    @Query("SELECT * FROM SENDERO WHERE id=:id")
    public Sendero getSendero(long id);

    @Query("SELECT * FROM SENDERO")
    public LiveData<List<Sendero>> getSenderos();
}

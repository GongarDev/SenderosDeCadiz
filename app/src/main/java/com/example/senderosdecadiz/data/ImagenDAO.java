package com.example.senderosdecadiz.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.senderosdecadiz.models.Imagen;
import java.util.List;

@Dao
public interface ImagenDAO {

    @Insert
    public long insertImagen(Imagen imagen);

    @Delete
    public int deleteImagen(Imagen imagen);

    @Query("SELECT * FROM IMAGEN WHERE id_fk=:id_fk")
    public LiveData<List<Imagen>> getImagenes(final long id_fk);

    @Query("SELECT * FROM IMAGEN")
    public LiveData<List<Imagen>> getImagenesList();
}

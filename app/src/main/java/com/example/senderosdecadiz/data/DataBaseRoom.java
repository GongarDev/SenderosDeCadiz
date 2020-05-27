package com.example.senderosdecadiz.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.senderosdecadiz.models.Imagen;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.models.Sitio;

@Database(entities = {Sitio.class, Sendero.class, Imagen.class}, version = 1, exportSchema = false)
public abstract class DataBaseRoom extends RoomDatabase {

    public abstract SitioDAO sitioDAO();
    public abstract FavoritoDAO favoritoDAO();
    public abstract ImagenDAO imagenDAO();
    private static DataBaseRoom INSTANCE=null;


    public static DataBaseRoom getInstance(final Context context){

        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DataBaseRoom.class, "senderosdecadiz.db").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=null;
    }
}

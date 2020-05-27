package com.example.senderosdecadiz.viewmodels;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.data.DataBaseRoom;
import com.example.senderosdecadiz.models.Imagen;

import java.util.List;

public class GaleriaFotosViewModel extends AndroidViewModel {

    private LiveData<List<Imagen>> imagenesList;
    private static DataBaseRoom db;

    public GaleriaFotosViewModel(@NonNull Application application) {
        super(application);
        db = DataBaseRoom.getInstance(application);
        imagenesList = db.imagenDAO().getImagenesList();
    }

    public LiveData<List<Imagen>> getImagenes(long id_fk) {
        return db.imagenDAO().getImagenes(id_fk);
    }

    public LiveData<List<Imagen>> getImagenesList() {
        return imagenesList;
    }

    public void addImagen(Imagen imagen) {
        new GaleriaFotosViewModel.AsyncAddImagenDB().execute(imagen);
    }

    public void deleteImagen(Imagen imagen){
        new GaleriaFotosViewModel.AsynDeleteImagenDB().execute(imagen);
    }

    private class AsyncAddImagenDB extends AsyncTask<Imagen, Void, Long> {

        Imagen imagen;

        @Override
        protected Long doInBackground(Imagen... imagenes) {

            long id = -1;

            if (imagenes.length != 0) {
                String name = imagenes[0].getPath();
                Log.v("Imagen: ", name);
                imagen = imagenes[0];
                id = db.imagenDAO().insertImagen(imagenes[0]);
                imagen.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1) {
                Toast.makeText(getApplication(), R.string.image_add_error, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplication(), R.string.image_added, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsynDeleteImagenDB extends AsyncTask<Imagen, Void, Integer> {

        public AsynDeleteImagenDB() {

        }

        @Override
        protected Integer doInBackground(Imagen... imagenes) {

            int deletedrows = 0;

            if (imagenes.length != 0) {
                deletedrows = db.imagenDAO().deleteImagen(imagenes[0]);
            }

            return deletedrows;
        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0) {
                Toast.makeText(getApplication(), R.string.image_delete_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.image_deleted, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

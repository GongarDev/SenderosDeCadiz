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
import com.example.senderosdecadiz.models.Sitio;
import java.util.List;

public class MisSitiosViewModel extends AndroidViewModel {

    private LiveData<List<Sitio>> sitiosList;
    private static DataBaseRoom db;

    public MisSitiosViewModel(@NonNull Application application) {
        super(application);
        db = DataBaseRoom.getInstance(application);
        sitiosList = db.sitioDAO().getSitios();
    }

    public LiveData<List<Sitio>> getSitios() {

        return sitiosList;
    }

    public void addSitio(Sitio sitio) {

        new AsyncAddSitioDB().execute(sitio);
    }

    public void updateSitio(Sitio sitio){
        new AsyncEditSitioDB().execute(sitio);
    }

    public void deleteSitio(Sitio sitio){
        new AsynDeleteSitioDB().execute(sitio);
    }

    private class AsyncAddSitioDB extends AsyncTask<Sitio, Void, Long> {

        Sitio sitio;

        @Override
        protected Long doInBackground(Sitio... sitios) {

            long id = -1;

            if (sitios.length != 0) {
                String name = sitios[0].getName();
                Log.d("Sitio", name);
                sitio = sitios[0];
                id = db.sitioDAO().insertSitio(sitios[0]);
                sitio.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {

            if (id == -1) {
                Toast.makeText(getApplication(), R.string.site_add_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.site_added, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsyncEditSitioDB extends AsyncTask<Sitio, Void, Integer> {

        public AsyncEditSitioDB() {

        }

        @Override
        protected Integer doInBackground(Sitio... sitios) {

            int updatedrows = 0;

            if (sitios.length != 0) {
                updatedrows = db.sitioDAO().updateSitio(sitios[0]);
            }

            return updatedrows;
        }

        @Override
        protected void onPostExecute(Integer updatedRows) {

            if (updatedRows == 0) {
                Toast.makeText(getApplication(), R.string.site_update_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.site_updated, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsynDeleteSitioDB extends AsyncTask<Sitio, Void, Integer> {

        public AsynDeleteSitioDB() {
        }

        @Override
        protected Integer doInBackground(Sitio... sitios) {

            int deletedrows = 0;

            if (sitios.length != 0) {
                deletedrows = db.sitioDAO().deleteSitio(sitios[0]);
            }

            return deletedrows;
        }

        @Override
        protected void onPostExecute(Integer deletedRows) {

            if (deletedRows == 0) {
                Toast.makeText(getApplication(), R.string.site_delete_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.site_deleted, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
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
import com.example.senderosdecadiz.models.Sendero;
import java.util.List;

public class FavoritosViewModel extends AndroidViewModel {

    private static LiveData<List<Sendero>> senderoList;
    private static DataBaseRoom db;

    public FavoritosViewModel(@NonNull Application application) {

        super(application);
        db = DataBaseRoom.getInstance(application);
        senderoList = db.favoritoDAO().getSenderos();
    }

    public LiveData<List<Sendero>> getSenderoList() {
        return senderoList;
    }

    public void addSendero(Sendero sendero) { new AsyncAddSenderoDB().execute(sendero); }

    public void deleteSendero(Sendero sendero) {
        new AsynDeleteSenderoDB().execute(sendero);
    }

    private class AsyncAddSenderoDB extends AsyncTask<Sendero, Void, Long> {

        Sendero sendero;

        @Override
        protected Long doInBackground(Sendero... senderos) {

            long id = -1;

            if (senderos.length != 0) {
                String name = senderos[0].getNombre();
                Log.d("Sitio", name);
                sendero = senderos[0];
                id = db.favoritoDAO().insertSendero(senderos[0]);
                sendero.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1) {
                Toast.makeText(getApplication(), R.string.sendero_add_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.sendero_added, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AsynDeleteSenderoDB extends AsyncTask<Sendero, Void, Integer> {

        public AsynDeleteSenderoDB() { }

        @Override
        protected Integer doInBackground(Sendero... senderos) {

            int deletedrows = 0;

            if (senderos.length != 0) {
                deletedrows = db.favoritoDAO().deleteSendero(senderos[0]);
            }

            return deletedrows;
        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0) {
                Toast.makeText(getApplication(), R.string.sendero_delete_error, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), R.string.sendero_deleted, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
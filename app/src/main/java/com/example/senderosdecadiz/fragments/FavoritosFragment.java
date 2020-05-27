package com.example.senderosdecadiz.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.adapters.FavoritoAdapter;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.viewmodels.FavoritosViewModel;
import com.example.senderosdecadiz.viewmodels.SenderosViewModel;

import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class FavoritosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FavoritoAdapter adapter;
    private SenderosViewModel senderosViewModel;
    private TextView emptyView;
    private FavoritosViewModel favoritosViewModel;
    private OnSenderoFavortioSelected callback;

    public FavoritosFragment() { }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favoritos, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewFavoritos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        emptyView = root.findViewById(R.id.NoFoundFavoritosTextView);

        ConnectivityManager connectivityManager= (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected= info != null && info.isConnected();

        final ImageView progressBar;
        progressBar = root.findViewById(R.id.progressBarFavoritos);
        Glide.with(getContext()).asGif().load(R.drawable.giphy).into(progressBar);

        if (isConnected){

            favoritosViewModel =
                    ViewModelProviders.of(this).get(FavoritosViewModel.class);
            senderosViewModel =
                    ViewModelProviders.of(this).get(SenderosViewModel.class);

            favoritosViewModel.getSenderoList().observe(this, new Observer<List<Sendero>>() {
                @Override
                public void onChanged(List<Sendero> senderos) {

                    progressBar.setVisibility(View.GONE);

                    if (senderos!=null) {

                        adapter = new FavoritoAdapter(senderos, R.layout.list_item_favoritos, getActivity(), new FavoritoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Sendero sendero, int position) {
                                callback.OnSenderoFavortioSelected(sendero);
                            }
                        }, new FavoritoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Sendero sendero, int position) {
                                deleteSendero(sendero);
                                List<Sendero> senderoList = senderosViewModel.getSenderosList().getValue();

                                for (Sendero s: senderoList) {
                                    if(s.getUri().equals(sendero.getUri())){
                                        s.setFavorito(false);
                                    }
                                }
                                sendero.setFavorito(false);
                            }
                        });

                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
        }

        return root;
    }

    private void deleteSendero(Sendero sendero) {
        favoritosViewModel.deleteSendero(sendero);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnSenderoFavortioSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnSenderoFavortioSelected");
        }
    }
    public interface OnSenderoFavortioSelected {
        public void OnSenderoFavortioSelected(Sendero sendero);
    }
}
package com.example.senderosdecadiz.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.adapters.SenderoAdapter;
import com.example.senderosdecadiz.models.Sendero;
import com.example.senderosdecadiz.viewmodels.FavoritosViewModel;
import com.example.senderosdecadiz.viewmodels.SenderosViewModel;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SenderosFragment extends Fragment {

    private RecyclerView recyclerView;
    private SenderoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SenderosViewModel senderosViewModel;
    private FavoritosViewModel favoritosViewModel;
    private TextView emptyView;
    private OnSenderoSelected callback;
    List<Sendero> senderoList;

    public SenderosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_senderos, container, false);

        setHasOptionsMenu(true);

        recyclerView = root.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        emptyView = root.findViewById(R.id.NoFoundTextView);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        final ImageView progressBar;
        progressBar = root.findViewById(R.id.progressBarSenderos);
        Glide.with(getContext()).asGif().load(R.drawable.giphy).into(progressBar);

        if (isConnected) {

            senderosViewModel =
                    ViewModelProviders.of(this).get(SenderosViewModel.class);

            favoritosViewModel =
                    ViewModelProviders.of(this).get(FavoritosViewModel.class);

            final LifecycleOwner owner = this.getViewLifecycleOwner();

            senderosViewModel.getSenderosList().observe(this, new Observer<List<Sendero>>() {
                @Override
                public void onChanged(List<Sendero> senderos) {

                    progressBar.setVisibility(View.GONE);

                    if (senderos != null) {

                        senderoList = senderos;

                        favoritosViewModel.getSenderoList().observe(owner, new Observer<List<Sendero>>() {
                            @Override
                            public void onChanged(List<Sendero> senderosFavoritos) {

                                if (adapter == null) {
                                    adapter = new SenderoAdapter(senderoList, R.layout.list_item_senderos, getActivity(), new SenderoAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Sendero sendero, int position) {
                                            callback.OnSenderoSelected(sendero);
                                        }
                                    }, new SenderoAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(Sendero sendero, int position) {

                                            if (sendero.isFavorito()) {
                                                sendero.setFavorito(false);
                                                deleteSenderoDb(sendero);
                                            } else {
                                                sendero.setFavorito(true);
                                                saveSenderoDb(sendero);
                                            }
                                        }
                                    });

                                    recyclerView.setAdapter(adapter);
                                }

                                if (senderoList != null && !senderoList.isEmpty()) {
                                    for (Sendero sF : senderosFavoritos) {
                                        for (Sendero s : senderoList) {
                                            if (sF.getUri().equals(s.getUri())) {
                                                s.setFavorito(true);
                                                s.setId(sF.getId());
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_connection);
        }

        return root;
    }

    private void saveSenderoDb(Sendero sendero) {
        favoritosViewModel.addSendero(sendero);
    }

    private void deleteSenderoDb(Sendero sendero) {
        favoritosViewModel.deleteSendero(sendero);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnSenderoSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnSenderoFavortioSelected");
        }
    }

    public interface OnSenderoSelected {
        public void OnSenderoSelected(Sendero sendero);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate menu
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (adapter != null){
                    adapter.getFilter().filter(newText);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }

                return true;
            }
        });
    }

}
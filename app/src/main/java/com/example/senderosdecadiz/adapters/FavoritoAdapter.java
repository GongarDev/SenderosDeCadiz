package com.example.senderosdecadiz.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.models.Sendero;

import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.ViewHolder> {

    private List<Sendero> senderos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerDelete;

    public FavoritoAdapter(List<Sendero> senderos, int layout, Activity activity,
                           OnItemClickListener listenerItem, OnItemClickListener listenerDelete ) {

        this.senderos = senderos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerDelete = listenerDelete;
    }

    @NonNull
    @Override
    public FavoritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        FavoritoAdapter.ViewHolder viewHolder = new FavoritoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoAdapter.ViewHolder viewHolder, final int i) {

        final Sendero sendero = senderos.get(i);

        viewHolder.textViewSenderoFavorito.setText(sendero.getNombre());
        viewHolder.textViewFavoritoMunicipio.setText(sendero.getMunicipio());

        viewHolder.containerSenderoFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(sendero, i);
            }
        });

        viewHolder.containerFavorito.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listenerDelete.onItemClick(sendero, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return senderos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSenderoFavorito;
        TextView textViewFavoritoMunicipio;
        ImageView imageViewBorrarFavorito;
        LinearLayout containerSenderoFavorito;
        LinearLayout containerFavorito;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSenderoFavorito = itemView.findViewById(R.id.textViewSenderoFavorito);
            textViewFavoritoMunicipio = itemView.findViewById(R.id.textViewFavoritoMunicipio);
            imageViewBorrarFavorito = itemView.findViewById(R.id.imageViewBorrarFavorito);
            containerSenderoFavorito = itemView.findViewById(R.id.container_SenderoFavorito);
            containerFavorito = itemView.findViewById(R.id.borrarFavorito);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Sendero sendero, int position);
    }
}
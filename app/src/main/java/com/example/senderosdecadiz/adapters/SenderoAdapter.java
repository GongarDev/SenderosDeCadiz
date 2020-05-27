package com.example.senderosdecadiz.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.models.Sendero;

import java.util.ArrayList;
import java.util.List;

public class SenderoAdapter extends RecyclerView.Adapter<SenderoAdapter.ViewHolder> implements Filterable {

    private List<Sendero> senderos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerFavorito;

    private List<Sendero> senderosFilter;

    public SenderoAdapter() {
    }

    public SenderoAdapter(List<Sendero> senderos, int layout, Activity activity, OnItemClickListener listenerItem, OnItemClickListener listenerFavorito) {

        this.senderos = senderos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerFavorito = listenerFavorito;
        this.senderosFilter = senderos;
    }

    @NonNull
    @Override
    public SenderoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SenderoAdapter.ViewHolder viewHolder, final int i) {

        Sendero sendero = senderos.get(i);

        //Aquí elegimos un icono u otro para la ImagenView de favoritos.
        if (sendero.isFavorito()) {
            viewHolder.imageViewFavorito.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            viewHolder.imageViewFavorito.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        viewHolder.textViewSendero.setText(sendero.getNombre());
        viewHolder.textViewSenderoMunicipio.setText(sendero.getMunicipio());

        viewHolder.containerSendero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sendero sendero = senderos.get(i);
                listenerItem.onItemClick(sendero, i);
            }
        });

        viewHolder.containerFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aquí cambiamos a un icono u otro para la ImagenView de favoritos cuando se clickee.
                Sendero sendero = senderos.get(i);
                if (!sendero.isFavorito()) {
                    viewHolder.imageViewFavorito.setImageResource(R.drawable.baseline_favorite_24);
                } else {
                    viewHolder.imageViewFavorito.setImageResource(R.drawable.baseline_favorite_border_24);
                }
                listenerFavorito.onItemClick(sendero, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return senderos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSendero;
        TextView textViewSenderoMunicipio;
        ImageView imageViewFavorito;
        LinearLayout containerSendero;
        LinearLayout containerFavorito;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewSendero = itemView.findViewById(R.id.textViewSendero);
            textViewSenderoMunicipio = itemView.findViewById(R.id.textViewSenderoMunicipio);
            imageViewFavorito = itemView.findViewById(R.id.imageViewfavorito);
            containerSendero = itemView.findViewById(R.id.sendero);
            containerFavorito = itemView.findViewById(R.id.favorito);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Sendero sendero, int position);
    }


    // Esta clase interna se encarga de filtar el adapter según un un parámetro dado.
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();

                if (charString.isEmpty()){
                    senderos = senderosFilter;
                }else{

                    List<Sendero> filterList = new ArrayList<>();

                    for (Sendero data : senderosFilter){

                        if (data.getMunicipio().toLowerCase().contains(charString)){
                            filterList.add(data);
                        }
                    }

                    senderos = filterList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = senderos;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                senderos = (List<Sendero>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}


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
import com.example.senderosdecadiz.models.Sitio;

import java.util.List;

public class SitioAdapter extends RecyclerView.Adapter<SitioAdapter.ViewHolder> {

    private List<Sitio> sitios;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerUpdate;
    private OnItemClickListener listenerDelete;

    public SitioAdapter(List<Sitio> sitios,int layout, Activity activity, OnItemClickListener listenerItem,
                        OnItemClickListener listenerUpdate, OnItemClickListener listenerDelete) {

        this.sitios = sitios;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerUpdate = listenerUpdate;
        this.listenerDelete = listenerDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(activity).inflate(layout,viewGroup, false );

        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SitioAdapter.ViewHolder viewHolder, final int i) {

        final Sitio sitio = sitios.get(i);

        viewHolder.textViewSitio.setText(sitio.getName());

        viewHolder.textViewSitioLocalidad.setText(sitio.getLocalidad());

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(sitio, i);
            }
        });

        viewHolder.imageViewEditSitioIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerUpdate.onItemClick(sitio, i);
            }
        });

        viewHolder.imageViewDeleteSitioIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerDelete.onItemClick(sitio, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sitios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        LinearLayout container;
        TextView textViewSitio;
        TextView textViewSitioLocalidad;
        ImageView imageViewEditSitioIcon;
        ImageView imageViewDeleteSitioIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container= itemView.findViewById(R.id.sitio);
            textViewSitio= itemView.findViewById(R.id.textViewSitio);
            textViewSitioLocalidad = itemView.findViewById(R.id.textViewSitioMunicipio);
            imageViewEditSitioIcon = itemView.findViewById(R.id.imageViewEditSitioIcon);
            imageViewDeleteSitioIcon = itemView.findViewById(R.id.imageViewDeleteSitioIcon);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Sitio sitio, int position);
    }
}

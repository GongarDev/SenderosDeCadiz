package com.example.senderosdecadiz.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.senderosdecadiz.R;
import com.example.senderosdecadiz.models.Imagen;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ViewHolder> {

    private List<Imagen> imagenes;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public ImagenAdapter() {}

    public ImagenAdapter(long idSitio, List<Imagen> imagenes, int layout, Activity activity, OnItemClickListener listenerItem) {



        List<Imagen> imagenesSitio = new ArrayList<Imagen>();

        //Comparamos la foreign key para filtar las imágenes.
        for (Imagen img: imagenes) {
            if (img.getId_fk()==idSitio){
                imagenesSitio.add(img);
            }
        }

        this.imagenes = imagenesSitio;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public ImagenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(activity).inflate(layout ,viewGroup, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImagenAdapter.ViewHolder viewHolder, final int i) {

        Imagen imagen = imagenes.get(i);

        File imgFile = new File(imagen.getPath());

        //Establecemos la imagen en miniatura correspondiente.
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        viewHolder.imageViewImagen.setImageBitmap(myBitmap);

        viewHolder.imageViewImagen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Imagen imagen = imagenes.get(i);
                listenerItem.onItemClick(imagen, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageViewImagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewImagen= itemView.findViewById(R.id.imageViewImagen);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Imagen imagen, int position);
    }
}

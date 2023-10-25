// MediaAdapter.java
package com.example.lm_seguridad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;

public class MediaAdapter extends BaseAdapter {
    private Context context;
    private StorageReference storageReference; // Aquí deberás implementar la lógica para recuperar fotos y videos

    public MediaAdapter(Context context, StorageReference storageReference) {
        this.context = context;
        this.storageReference = storageReference;
    }

    @Override
    public int getCount() {
        // Devuelve la cantidad de elementos en tu galería
        // Por ejemplo, la cantidad de fotos y videos que tienes
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // Devuelve el elemento en la posición dada
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // Infla la vista si no existe una disponible
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        // Implementa la lógica para cargar y mostrar fotos y videos aquí
        // Puedes usar Glide o Picasso para cargar imágenes desde Firebase Storage

        return imageView;
    }
}


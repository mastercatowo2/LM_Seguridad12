package com.example.lm_seguridad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class MediaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<StorageReference> storageReferences;

    public MediaAdapter(Context context, ArrayList<StorageReference> storageReferences) {
        this.context = context;
        this.storageReferences = storageReferences;
    }

    @Override
    public int getCount() {
        return storageReferences.size();
    }

    @Override
    public Object getItem(int position) {
        return storageReferences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        // Obtiene la referencia de Firebase Storage para la posición actual
        StorageReference storageReference = storageReferences.get(position);

        // Carga la imagen usando Glide
        Glide.with(context)
                .load(storageReference)
                .into(imageView);

        return imageView;
    }
}

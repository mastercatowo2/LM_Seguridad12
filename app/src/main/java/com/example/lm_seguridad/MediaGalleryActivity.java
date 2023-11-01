package com.example.lm_seguridad;

import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class MediaGalleryActivity extends AppCompatActivity {
    private GridView mediaGridView;
    private MediaAdapter mediaAdapter;
    private ArrayList<StorageReference> storageReferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_gallery);

        // Inicializa el StorageReference de Firebase Storage y obtén las referencias de las imágenes
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("/profile_images");

        // Agrega las referencias de las imágenes que deseas mostrar en un ArrayList
        storageReferences = new ArrayList<>();
        storageReferences.add(storageReference.child("1698199755766.jpg"));
        storageReferences.add(storageReference.child("1698200699486.jpg"));
        // Agrega más referencias si es necesario...

        mediaGridView = findViewById(R.id.mediaGridView);
        mediaAdapter = new MediaAdapter(this, storageReferences);
        mediaGridView.setAdapter(mediaAdapter);
    }
}

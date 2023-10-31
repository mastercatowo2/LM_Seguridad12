// MediaGalleryActivity.java
package com.example.lm_seguridad;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MediaGalleryActivity extends AppCompatActivity {
    private GridView mediaGridView;
    private MediaAdapter mediaAdapter;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_gallery);

        // Inicializa el StorageReference de Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        mediaGridView = findViewById(R.id.mediaGridView);
        mediaAdapter = new MediaAdapter(this, storageReference); // Implementa MediaAdapter para mostrar fotos y videos
        mediaGridView.setAdapter(mediaAdapter);
    }
}
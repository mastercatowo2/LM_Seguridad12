package com.example.lm_seguridad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Perfil1 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivFoto;
    private StorageReference storageReference;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PROFILE_IMAGE_URL_KEY = "profileImageUrl";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil1);

        ivFoto = findViewById(R.id.ivFoto);
        storageReference = FirebaseStorage.getInstance().getReference();

        // Cargar la imagen del SharedPreferences al ImageView
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String imageUrl = prefs.getString(PROFILE_IMAGE_URL_KEY, null);
        if (imageUrl != null) {
            // Cargar y mostrar la imagen en el ImageView con Glide
            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(this)
                    .load(imageUrl)
                    .apply(requestOptions)
                    .into(ivFoto);
        }

        Button buttonUpload = findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            uploadImage(imageUri);
        }
    }

    private void uploadImage(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("profile_images/" + System.currentTimeMillis());

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getStorage().getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Cargar y mostrar la imagen en el ImageView con Glide
                                            RequestOptions requestOptions = new RequestOptions()
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL);

                                            Glide.with(Perfil1.this)
                                                    .load(uri)
                                                    .apply(requestOptions)
                                                    .into(ivFoto);

                                            // Guardar la URL de la imagen en SharedPreferences
                                            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                            editor.putString(PROFILE_IMAGE_URL_KEY, uri.toString());
                                            editor.apply();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Firebase Storage", "Error al obtener la URL de la imagen: " + e.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Firebase Storage", "Error al cargar la imagen: " + e.getMessage());
                            Toast.makeText(Perfil1.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
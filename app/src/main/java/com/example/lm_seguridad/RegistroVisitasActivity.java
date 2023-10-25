package com.example.lm_seguridad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistroVisitasActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextRut;
    private Button buttonGuardarVisita;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_visitas);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextRut = findViewById(R.id.editTextRut);
        buttonGuardarVisita = findViewById(R.id.buttonGuardarVisita);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonGuardarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarVisita();
            }
        });
    }

    private void guardarVisita() {
        String nombre = editTextNombre.getText().toString().trim();
        String rut = editTextRut.getText().toString().trim();

        // Validación del Rut (sólo números y 8 dígitos)
        if (!rut.matches("[0-9]{1,8}")) {
            Toast.makeText(this, "Rut inválido. Deben ser de 1 a 8 dígitos numéricos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el ID del usuario actualmente autenticado en Firebase
        String userId = auth.getCurrentUser().getUid();

        // Crear un objeto para la visita
        Visita visita = new Visita(nombre, rut, userId);

        // Guardar la visita en Firebase Firestore
        db.collection("visitas")
                .add(visita)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Visita guardada exitosamente.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar la visita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

package com.example.lm_seguridad;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroVisitasActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextRut;
    private Button buttonGuardarVisita;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_visitas);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextRut = findViewById(R.id.editTextRut);
        buttonGuardarVisita = findViewById(R.id.buttonGuardarVisita);
        auth = FirebaseAuth.getInstance();

        // Obtén una referencia a la ubicación en Firebase Realtime Database donde se guardarán las visitas
        databaseReference = FirebaseDatabase.getInstance().getReference("visitas");

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

        // Genera una nueva clave para la visita en la base de datos
        String visitaKey = databaseReference.push().getKey();

        // Guardar la visita en Firebase Realtime Database utilizando la clave generada
        databaseReference.child(visitaKey).setValue(visita)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Visita guardada exitosamente.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al guardar la visita: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

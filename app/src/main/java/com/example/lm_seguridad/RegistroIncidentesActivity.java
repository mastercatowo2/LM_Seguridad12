package com.example.lm_seguridad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.BuildConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class RegistroIncidentesActivity extends AppCompatActivity {
    private EditText etMagnitud;
    private EditText etComentarios;
    private DatabaseReference incidentesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_incidentes_activity);
        etMagnitud = findViewById(R.id.etMagnitud);
        etComentarios = findViewById(R.id.etComentarios);

        // Habilitar depuración de Firebase (agrega este código)
        if (BuildConfig.DEBUG) {
            FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        }

        // Obtiene una referencia a la ubicación en Firebase Realtime Database donde se guardarán los incidentes
        incidentesRef = FirebaseDatabase.getInstance().getReference("incidentes");
    }

    public void registrarIncidente(View view) {
        String magnitudStr = etMagnitud.getText().toString();
        String comentarios = etComentarios.getText().toString();

        // Validar que la magnitud esté en el rango (1-7)
        int magnitud = Integer.parseInt(magnitudStr);
        if (magnitud >= 1 && magnitud <= 7) {
            // La magnitud es válida, puedes registrar el incidente aquí
            // Crea un objeto Incidente con los datos
            Incidente incidente = new Incidente(magnitud, comentarios);

            // Genera una nueva clave para el incidente en la base de datos
            String incidenteKey = incidentesRef.push().getKey();

            // Guarda el incidente en la base de datos utilizando la clave generada
            incidentesRef.child(incidenteKey).setValue(incidente);

            Toast.makeText(this, "Incidente registrado", Toast.LENGTH_SHORT).show();
        } else {
            // Muestra un mensaje de error en el logcat
            Log.e("RegistroIncidentesActivity", "La magnitud no está en el rango 1-7");

            Toast.makeText(this, "La magnitud debe estar en el rango 1-7", Toast.LENGTH_SHORT).show();
        }
    }

    // Resto de tu código si lo tienes...
}
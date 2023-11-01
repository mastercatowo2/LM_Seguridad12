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

        if (BuildConfig.DEBUG) {
            FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        }

        incidentesRef = FirebaseDatabase.getInstance().getReference("incidentes");
    }

    public void registrarIncidente(View view) {
        String magnitudStr = etMagnitud.getText().toString();
        String comentarios = etComentarios.getText().toString();

        try {
            int magnitud = Integer.parseInt(magnitudStr);
            if (magnitud >= 1 && magnitud <= 7) {
                Incidente incidente = new Incidente(magnitud, comentarios);

                String incidenteKey = incidentesRef.push().getKey();

                incidentesRef.child(incidenteKey).setValue(incidente);

                Toast.makeText(this, "Incidente registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "La magnitud debe estar en el rango 1-7", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese una magnitud válida", Toast.LENGTH_SHORT).show();
            Log.e("RegistroIncidentesActivity", "Error al convertir la magnitud a número", e);
        }
    }
}

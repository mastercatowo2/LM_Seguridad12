package com.example.lm_seguridad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngresarMallaTurnosActivity extends AppCompatActivity {
    private EditText nombreTurnoEditText;
    private Spinner diaSpinner;
    private Spinner estadoTurnoSpinner;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_malla_turnos);

        nombreTurnoEditText = findViewById(R.id.nombreTurnoEditText);
        diaSpinner = findViewById(R.id.diaSpinner);
        estadoTurnoSpinner = findViewById(R.id.estadoTurnoSpinner);

        mDatabase = FirebaseDatabase.getInstance().getReference("mallas_turno");
    }

    public void guardarMallaDeTurnos(View view) {
        String nombreTurno = nombreTurnoEditText.getText().toString();
        String dia = diaSpinner.getSelectedItem().toString();
        String estadoTurno = estadoTurnoSpinner.getSelectedItem().toString();

        if (nombreTurno.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nombre del turno", Toast.LENGTH_SHORT).show();
            return;
        }

        String mallaId = mDatabase.push().getKey();

        Turno turno = new Turno(dia, estadoTurno);

        if (mallaId != null) {
            mDatabase.child(mallaId).setValue(turno)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Malla de turnos guardada correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al guardar la malla de turnos", Toast.LENGTH_SHORT).show();
                            if (task.getException() != null) {
                                Log.e("FirebaseError", task.getException().getMessage());
                            } else {
                                Log.e("FirebaseError", "Unknown error occurred while saving data to Firebase");
                            }
                        }
                    });
        } else {
            Log.e("FirebaseError", "mallaId es null. No se puede guardar la malla de turnos en Firebase.");
            Toast.makeText(this, "Error al guardar la malla de turnos", Toast.LENGTH_SHORT).show();
        }
    }
}

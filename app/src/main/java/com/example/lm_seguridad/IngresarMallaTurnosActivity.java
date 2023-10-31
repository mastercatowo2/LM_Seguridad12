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

        // Obtén la referencia de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference("mallas_turno");
    }

    public void guardarMallaDeTurnos(View view) {
        String nombreTurno = nombreTurnoEditText.getText().toString();
        String dia = diaSpinner.getSelectedItem().toString();
        String estadoTurno = estadoTurnoSpinner.getSelectedItem().toString();

        // Validación de datos (puedes agregar más validaciones según tus necesidades)
        if (nombreTurno.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa el nombre del turno", Toast.LENGTH_SHORT).show();
            return;
        }

        // Genera un ID único para la malla de turnos
        String mallaId = mDatabase.push().getKey();

        // Crea un objeto Turno con los datos
        Turno turno = new Turno(dia, estadoTurno);

        // Verifica que mallaId no sea null antes de guardar en Firebase
        if (mallaId != null) {
            // Guarda la malla de turnos en la base de datos bajo el nodo "mallas_turno" con el ID generado
            mDatabase.child(mallaId).setValue(turno)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Operación completada con éxito
                            Toast.makeText(this, "Malla de turnos guardada correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // Error al guardar en la base de datos
                            Toast.makeText(this, "Error al guardar la malla de turnos", Toast.LENGTH_SHORT).show();
                            // Registra el mensaje de error en los logs
                            if (task.getException() != null) {
                                Log.e("FirebaseError", task.getException().getMessage());
                            } else {
                                Log.e("FirebaseError", "Unknown error occurred while saving data to Firebase");
                            }
                        }
                    });
        } else {
            // Manejar el caso cuando mallaId es null
            Log.e("FirebaseError", "mallaId es null. No se puede guardar la malla de turnos en Firebase.");
            Toast.makeText(this, "Error al guardar la malla de turnos", Toast.LENGTH_SHORT).show();
        }
    }
}

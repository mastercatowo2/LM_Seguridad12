// IngresarMallaTurnosActivity.java
package com.example.lm_seguridad;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IngresarMallaTurnosActivity extends AppCompatActivity {
    private EditText nombreTurnoEditText;
    private EditText horarioTurnoEditText;
    private Spinner estadoTurnoSpinner;
    private boolean esAdministrador = true; // Ajusta según el usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_malla_turnos);

        nombreTurnoEditText = findViewById(R.id.nombreTurnoEditText);
        horarioTurnoEditText = findViewById(R.id.horarioTurnoEditText);
        estadoTurnoSpinner = findViewById(R.id.estadoTurnoSpinner);

        if (!esAdministrador) {
            // Muestra un mensaje de error si el usuario no es administrador.
            Toast.makeText(this, "No tienes permisos suficientes", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad
        }
    }

    public void guardarMallaDeTurnos(View view) {
        if (esAdministrador) {
            String nombreTurno = nombreTurnoEditText.getText().toString();
            String horarioTurno = horarioTurnoEditText.getText().toString();
            String estadoTurno = estadoTurnoSpinner.getSelectedItem().toString();

            // Realiza la validación de datos y envía la información a tu fuente de datos o base de datos.
            // Puedes utilizar Firebase Realtime Database, SQLite, u otro método de almacenamiento.
        } else {
            Toast.makeText(this, "No tienes permisos suficientes", Toast.LENGTH_SHORT).show();
        }
    }
}

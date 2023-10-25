
package com.example.lm_seguridad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MallasDeTurnosActivity extends AppCompatActivity {
    private ListView listView;
    private boolean esAdministrador = true; // Ajusta según el usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mallas_de_turnos_activity);

        listView = findViewById(R.id.listView);

        // Verifica si el usuario es administrador antes de permitir el acceso
        if (!esAdministrador) {
            // Muestra un mensaje de error si el usuario no es administrador
            // y evita abrir la actividad de mallas de turnos.
            // También podrías ocultar el botón de ingresar mallas de turnos.
            Toast.makeText(this, "No tienes permisos suficientes", Toast.LENGTH_SHORT).show();
        } else {
            // El usuario es administrador, continúa con la carga de mallas de turnos.
            cargarMallasDeTurnos();
        }
    }

    public void ingresarMallaDeTurnos(View view) {
        if (esAdministrador) {
            Intent intent = new Intent(this, IngresarMallaTurnosActivity.class);
            startActivity(intent);
        } else {
            // Muestra un mensaje de error si el usuario no es administrador
            // y evita abrir la actividad de ingreso de mallas de turnos.
            Toast.makeText(this, "No tienes permisos suficientes", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarMallasDeTurnos() {
        // Implementa la carga de mallas de turnos desde tu fuente de datos.
        // Supongamos que tienes una lista de mallas de turnos (objetos Turno).
        List<Turno> turnos = obtenerMallasDeTurnosDesdeFuenteDeDatos();

        // Crea un adaptador de turno
        TurnoAdapter adapter = new TurnoAdapter(this, turnos);

        // Asigna el adaptador al ListView
        listView.setAdapter(adapter);
    }

    private List<Turno> obtenerMallasDeTurnosDesdeFuenteDeDatos() {
        // Aquí debes implementar la lógica para obtener las mallas de turnos desde tu fuente de datos.
        // Puedes reemplazar este código con la lógica real según tu aplicación.
        List<Turno> turnos = new ArrayList<>();
        turnos.add(new Turno("Turno Lunes", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Martes", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Miércoles", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Jueves", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Viernes", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Sábado", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Turno Domingo", "8:00 AM -\n 20:00 PM", true));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));
        turnos.add(new Turno("Descanso", "Descanso", false));

        return turnos;
    }
}

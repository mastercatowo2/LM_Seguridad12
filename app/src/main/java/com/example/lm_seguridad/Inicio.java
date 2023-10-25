package com.example.lm_seguridad;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;  // Importa la clase Log
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class Inicio extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private Fragment fragmentActual = null;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.opChat) {
                    Toast.makeText(Inicio.this, "Ir al chat", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Inicio.this, Chat.class);
                    startActivity(intent);
                } else if (id == R.id.op2) {
                    Intent intent = new Intent(Inicio.this, MallasDeTurnosActivity.class);
                    startActivity(intent);
                } else if (id == R.id.op3) {
                    Intent intent = new Intent(Inicio.this, MediaGalleryActivity.class);
                    startActivity(intent);
                } else if (id == R.id.op6) {
                    Intent intent = new Intent(Inicio.this, RegistroIncidentesActivity.class);
                    startActivity(intent);
                } else if (id == R.id.op5) {
                    Intent intent = new Intent(Inicio.this, IngresarMallaTurnosActivity.class);
                    startActivity(intent);
                } else if (id == R.id.op7) {
                    Intent intent =  new Intent(Inicio.this, RegistroVisitasActivity.class);
                    startActivity(intent);
                } else if (id == R.id.opPerfil) {
                    Intent intent = new Intent(Inicio.this, Perfil1.class);
                    startActivity(intent);
                } else if (id == R.id.opTerminos) {
                    cargarFragmento(new Terminos());
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void cargarFragmento(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contener, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            fragmentActual = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.op4) { // Cambia a R.id.op4 que es el ítem para llamar a Carabineros
            mostrarDialogoEmergencias();
            return true;
        } else {
            // Resto del código para manejar otros elementos del menú
            // ...
        }
        return super.onOptionsItemSelected(item);
    }

    public void mostrarDialogoEmergencias() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                realizarLlamadaCarabineros();
            }
        });

        dialogView.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(Inicio.this, "Llamada cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void realizarLlamadaCarabineros() {
        String numeroCarabineros = "+56938726189";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("LlamadaCarabineros", "Permiso de llamada concedido. Iniciando llamada...");

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + numeroCarabineros));

            if (intent.resolveActivity(getPackageManager()) != null) {
                Log.d("LlamadaCarabineros", "Intent válido. Iniciando llamada...");
                startActivity(intent);
            } else {
                Log.e("LlamadaCarabineros", "No se puede realizar la llamada. Aplicación no compatible.");
                Toast.makeText(this, "No se puede realizar la llamada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("LlamadaCarabineros", "Permiso de llamada denegado. Solicitando permiso...");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                realizarLlamadaCarabineros();
            } else {
                Log.e("LlamadaCarabineros", "Permiso de llamada denegado por el usuario.");
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
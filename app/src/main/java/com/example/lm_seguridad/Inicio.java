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
                }else if (id == R.id.op4) {
                    Intent intent = new Intent(Inicio.this, LlamarCarabineros.class);
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


    }

package com.example.lm_seguridad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LlamarCarabineros extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        Button btnSi = findViewById(R.id.btnSi);
        Button btnNo = findViewById(R.id.btnNo);

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LlamarCarabineros.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Si el permiso no está otorgado, solicítalo al usuario.
                    ActivityCompat.requestPermissions(LlamarCarabineros.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
                } else {
                    // Si el permiso está otorgado, realiza la llamada.
                    realizarLlamadaCarabineros();
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void realizarLlamadaCarabineros() {
        String numeroCarabineros = "+56938726189";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numeroCarabineros));

        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        finish();
    }

    // Este método maneja el resultado de la solicitud de permisos.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el permiso fue otorgado, realiza la llamada.
                realizarLlamadaCarabineros();
            } else {
                // Si el permiso fue denegado, muestra un mensaje al usuario.
                // Puedes manejar este caso según tus necesidades.
            }
        }
    }
}

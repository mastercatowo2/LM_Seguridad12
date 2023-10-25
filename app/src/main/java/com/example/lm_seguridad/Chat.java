package com.example.lm_seguridad;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {
    private EditText messageEditText;
    private Button sendButton;
    private ListView messageListView;

    private DatabaseReference databaseReference;
    private ArrayAdapter<String> messageAdapter;

    private String chatName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // Obtiene el chatName de los extras del Intent
        chatName = getIntent().getStringExtra("chatName");

        if (chatName == null) {
            // Manejar el caso en el que chatName es nulo (por ejemplo, mostrar un mensaje de error)
            // Aquí puedes personalizar cómo manejar esta situación
            // Por ahora, solo terminaremos la actividad
            finish();
            return;
        }

        // Inicializa la base de datos de Firebase utilizando chatName como referencia
        databaseReference = FirebaseDatabase.getInstance().getReference().child("chats").child(chatName);

        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        messageListView = findViewById(R.id.messageListView);

        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        messageListView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(view -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Obtiene el ID del usuario actual
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    String userId = auth.getCurrentUser().getUid();
                    // Guarda un nuevo mensaje en Firebase
                    DatabaseReference newMessageRef = databaseReference.push();
                    newMessageRef.setValue(new Mensaje(messageText, userId));
                    messageEditText.setText("");
                }
            }
        });

        // Escucha cambios en la base de datos y muestra los mensajes
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                Mensaje message = dataSnapshot.getValue(Mensaje.class);
                if (message != null) {
                    messageAdapter.add(message.getText());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


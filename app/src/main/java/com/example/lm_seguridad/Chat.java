package com.example.lm_seguridad;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {
    private String chatName;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatName = getIntent().getStringExtra("chatName");
        if (chatName == null) {
            finish();
            return;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("chats").child(chatName);

        EditText messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);
        ListView messageListView = findViewById(R.id.messageListView);

        messageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        messageListView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(view -> {
            String messageText = messageEditText.getText().toString().trim();
            if (!messageText.isEmpty()) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference newMessageRef = databaseReference.push();
                    newMessageRef.setValue(new Mensaje(messageText, userId));
                    messageEditText.setText("");
                }
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                try {
                    Mensaje message = dataSnapshot.getValue(Mensaje.class);
                    if (message != null) {
                        messageAdapter.add(message.getText());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

package com.example.lm_seguridad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatSelectionActivity extends AppCompatActivity {
    private ListView chatListView;
    private DatabaseReference chatsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_selection_activity);

        chatListView = findViewById(R.id.chatListView);
        chatsReference = FirebaseDatabase.getInstance().getReference().child("chats");

        chatsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> chatList = new ArrayList<>();
                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    String chatName = chatSnapshot.getKey();
                    chatList.add(chatName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ChatSelectionActivity.this, android.R.layout.simple_list_item_1, chatList);
                chatListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de base de datos si es necesario
            }
        });

        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedChatName = (String) parent.getItemAtPosition(position);
                Log.d("ChatSelectionActivity", "Selected Chat: " + selectedChatName);
                Intent intent = new Intent(ChatSelectionActivity.this, Chat.class);
                intent.putExtra("chatName", selectedChatName);
                startActivity(intent);
            }
        });
    }
}

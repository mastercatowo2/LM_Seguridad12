    package com.example.lm_seguridad;

    import android.content.Intent;
    import android.os.Bundle;
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chat_selection_activity);

            chatListView = findViewById(R.id.chatListView);

            // Conecta a Firebase y carga la lista de chats de grupo disponibles
            DatabaseReference chatsReference = FirebaseDatabase.getInstance().getReference().child("chats");
            chatsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Recupera la lista de chats de grupo y muestra en la vista
                    List<String> chatList = new ArrayList<>();
                    for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                        String chatName = chatSnapshot.child("name").getValue(String.class);
                        chatList.add(chatName);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ChatSelectionActivity.this, android.R.layout.simple_list_item_1, chatList);
                    chatListView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Maneja errores, si es necesario
                }
            });

            // Configura un Listener para abrir el chat seleccionado
            chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Obtén el nombre del chat seleccionado
                    String selectedChatName = (String) parent.getItemAtPosition(position);

                    // Inicia la actividad de chat con el nombre del chat
                    Intent intent = new Intent(ChatSelectionActivity.this, Chat.class);
                    intent.putExtra("chatName", selectedChatName); // Pasa el chatName
                    startActivity(intent);
                }
            });
        }
    }
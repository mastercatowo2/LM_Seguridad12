package com.example.lm_seguridad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MensajeAdapter extends ArrayAdapter<Mensaje> {
    private List<Mensaje> mensajes;

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        super(context, 0, mensajes);
        this.mensajes = mensajes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mensaje mensaje = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        TextView messageText = convertView.findViewById(R.id.messageText);
        if (mensaje != null) {
            messageText.setText(mensaje.getText());
        }

        return convertView;
    }
}

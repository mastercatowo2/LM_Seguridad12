package com.example.lm_seguridad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lm_seguridad.Mensaje;
import com.example.lm_seguridad.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MensajeAdapter extends ArrayAdapter<Mensaje> {

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        super(context, 0, mensajes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mensaje mensaje = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        TextView messageText = convertView.findViewById(R.id.messageText);
        TextView senderName = convertView.findViewById(R.id.senderName);
        TextView timestamp = convertView.findViewById(R.id.timestamp);

        if (mensaje != null) {
            messageText.setText(mensaje.getText());
            senderName.setText(mensaje.getUserId());
            timestamp.setText(formatTimestamp(mensaje.getTimestamp()));
        }

        return convertView;
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}

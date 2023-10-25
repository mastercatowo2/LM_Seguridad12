package com.example.lm_seguridad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TurnoAdapter extends ArrayAdapter<Turno> {

    public TurnoAdapter(Context context, List<Turno> turnos) {
        super(context, 0, turnos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Turno turno = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_turno, parent, false);
        }

        TextView nombreTurno = convertView.findViewById(R.id.nombreTurno);
        TextView horarioTurno = convertView.findViewById(R.id.horarioTurno);

        if (turno != null) {
            nombreTurno.setText(turno.getNombre());
            horarioTurno.setText(turno.getHorario());
        }

        return convertView;
    }
}

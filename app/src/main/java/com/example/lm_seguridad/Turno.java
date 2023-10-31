package com.example.lm_seguridad;

public class Turno {
    private final String nombre;
    private final String horario;
    private final boolean esDiaDeTrabajo;

    public Turno(String nombre, String horario, boolean esDiaDeTrabajo) {
        this.nombre = nombre;
        this.horario = horario;
        this.esDiaDeTrabajo = esDiaDeTrabajo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }

    public boolean esDiaDeTrabajo() {
        return esDiaDeTrabajo;
    }
}

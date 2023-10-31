package com.example.lm_seguridad;

public class Turno {
    private final String dia;
    private final String estado;

    public Turno(String dia, String estado) {
        this.dia = dia;
        this.estado = estado;
    }

    public String getDia() {
        return dia;
    }

    public String getEstado() {
        return estado;
    }
}

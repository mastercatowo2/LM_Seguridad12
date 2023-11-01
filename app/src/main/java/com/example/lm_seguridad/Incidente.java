package com.example.lm_seguridad;

public class Incidente {
    private int magnitud;
    private String comentarios;

    public Incidente() {
    }

    public Incidente(int magnitud, String comentarios) {
        this.magnitud = magnitud;
        this.comentarios = comentarios;
    }

    public int getMagnitud() {
        return magnitud;
    }

    public String getComentarios() {
        return comentarios;
    }
}
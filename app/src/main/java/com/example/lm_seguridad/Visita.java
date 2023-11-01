package com.example.lm_seguridad;

public class Visita {
    private String nombre;
    private String rut;
    private String userId;

    public Visita() {
    }

    public Visita(String nombre, String rut, String userId) {
        this.nombre = nombre;
        this.rut = rut;
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRut() {
        return rut;
    }

    public String getUserId() {
        return userId;
    }
}

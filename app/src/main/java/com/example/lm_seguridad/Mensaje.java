package com.example.lm_seguridad;

public class Mensaje {
    private String text;
    private String userId;

    public Mensaje() {
        // Constructor vacío necesario para Firebase
    }

    public Mensaje(String text, String userId) {
        this.text = text;
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }
}



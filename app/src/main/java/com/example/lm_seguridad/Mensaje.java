package com.example.lm_seguridad;

public class Mensaje {
    private String text;
    private String userId;
    private long timestamp;

    public Mensaje(String messageText, String userId) {
    }

    public Mensaje(String text, String userId, long timestamp) {
        this.text = text;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

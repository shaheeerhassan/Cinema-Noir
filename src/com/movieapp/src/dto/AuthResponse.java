package com.movieapp.src.dto;

public class AuthResponse {
    private int userId;
    private String username;
    private String message;
    private boolean success;

    public AuthResponse() {}

    public AuthResponse(int userId, String username, String message, boolean success) {
        this.userId = userId;
        this.username = username;
        this.message = message;
        this.success = success;
    }

    // Getters & Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

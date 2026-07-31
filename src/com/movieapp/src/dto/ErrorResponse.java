package com.movieapp.src.dto;

public class ErrorResponse {
    private boolean success;
    private String message;
    private String errorCode;
    private int httpStatus;

    public ErrorResponse() {}

    public ErrorResponse(String message, String errorCode, int httpStatus) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    // Getters & Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}

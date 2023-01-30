package com.ruwan.reacongym.models;

public class ResponseModel {
    private int status;
    private String message;
    private boolean valid;

    public ResponseModel(int status, String message, boolean valid) {
        this.status = status;
        this.message = message;
        this.valid = valid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

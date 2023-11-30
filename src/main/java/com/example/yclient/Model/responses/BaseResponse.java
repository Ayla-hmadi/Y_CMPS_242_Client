package com.example.yclient.Model.responses;

public abstract class BaseResponse {
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}


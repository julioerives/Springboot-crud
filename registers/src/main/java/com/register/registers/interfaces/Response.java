package com.register.registers.interfaces;

public class Response<T> {
    private T data;
    private String message;
    private Boolean error;

    public Response(T data, String message, Boolean error) {
        this.data = data;
        this.message = message;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}

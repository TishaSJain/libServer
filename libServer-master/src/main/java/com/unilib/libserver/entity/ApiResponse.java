package com.unilib.libserver.entity;

public class ApiResponse<T> {
    public boolean success;
    public T data;
    public String errorMessage;
    public ApiResponse(){
        this.errorMessage=null;
    }
}

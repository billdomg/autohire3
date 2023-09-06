package com.carrental.autohire.dto;

public class ErrorResponse {

    private String message;

    private int code ;

    public ErrorResponse(String message,int code) {
        this.message = message;
        this.code = code;
        System.out.println(this.code + " " + this.message);
    }
}
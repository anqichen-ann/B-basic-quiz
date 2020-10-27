package com.example.demo.exception;

public class SourceNotFoundException extends RuntimeException{
    public SourceNotFoundException(String message){
        super(message);
    }
}

package com.example.exception;

public class NotAuthorisedException extends Exception{
    public NotAuthorisedException(String message){
        super(message);
    }
}

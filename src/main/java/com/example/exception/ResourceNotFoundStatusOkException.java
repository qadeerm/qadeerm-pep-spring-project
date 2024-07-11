package com.example.exception;

public class ResourceNotFoundStatusOkException extends RuntimeException {
    public ResourceNotFoundStatusOkException(String ex){
        super(ex);
    }
}

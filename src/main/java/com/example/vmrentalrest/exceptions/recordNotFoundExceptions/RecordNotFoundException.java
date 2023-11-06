package com.example.vmrentalrest.exceptions.recordNotFoundExceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String message) {
        super(message);
    }
}

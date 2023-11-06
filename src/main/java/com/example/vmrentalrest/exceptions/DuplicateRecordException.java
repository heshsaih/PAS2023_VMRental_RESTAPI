package com.example.vmrentalrest.exceptions;

public class DuplicateRecordException extends Exception{
    public DuplicateRecordException() {
        super("Record already exists");
    }
}

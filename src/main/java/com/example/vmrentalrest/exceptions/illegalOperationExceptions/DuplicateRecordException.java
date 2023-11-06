package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class DuplicateRecordException extends IllegalOperationException{
    public DuplicateRecordException() {
        super("Record already exists");
    }
}

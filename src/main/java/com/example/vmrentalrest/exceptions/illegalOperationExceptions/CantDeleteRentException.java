package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class CantDeleteRentException extends IllegalOperationException{
    public CantDeleteRentException() {
        super("Can't delete rent");
    }
}

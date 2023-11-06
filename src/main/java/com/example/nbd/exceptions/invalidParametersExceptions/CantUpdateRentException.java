package com.example.nbd.exceptions.invalidParametersExceptions;

public class CantUpdateRentException extends InvalidParametersException {
    public CantUpdateRentException() {
        super("Can't update rent");
    }
}

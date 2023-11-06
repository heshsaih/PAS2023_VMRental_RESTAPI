package com.example.vmrentalrest.exceptions.invalidParametersExceptions;

public class CantUpdateRentException extends InvalidParametersException {
    public CantUpdateRentException() {
        super("Can't update rent");
    }
}

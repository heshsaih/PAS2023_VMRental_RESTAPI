package com.example.nbd.exceptions.invalidParametersExceptions;

public class UnknownUserTypeException extends InvalidParametersException {

    public UnknownUserTypeException () {
        super("Unknown user type");
    }
}

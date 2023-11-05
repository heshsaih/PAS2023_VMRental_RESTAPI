package com.example.nbd.exceptions;

public class UnknownUserTypeException extends UnknownEnumException {

    public UnknownUserTypeException () {
        super("Unknown user type");
    }
}

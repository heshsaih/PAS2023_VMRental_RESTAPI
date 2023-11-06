package com.example.nbd.exceptions.invalidParametersExceptions;

public class InvalidDatesException extends InvalidParametersException {
    public InvalidDatesException() {
        super("The dates are invalid");
    }
}

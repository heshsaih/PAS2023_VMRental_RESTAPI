package com.example.vmrentalrest.exceptions.invalidParametersExceptions;

public class InvalidDatesException extends InvalidParametersException {
    public InvalidDatesException() {
        super("The dates are invalid");
    }
}

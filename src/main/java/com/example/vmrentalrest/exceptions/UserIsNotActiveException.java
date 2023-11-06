package com.example.vmrentalrest.exceptions;

public class UserIsNotActiveException extends Exception {
    public UserIsNotActiveException() {
        super("Client is not active");
    }
}

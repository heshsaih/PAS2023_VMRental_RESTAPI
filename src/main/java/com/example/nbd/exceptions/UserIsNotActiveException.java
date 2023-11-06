package com.example.nbd.exceptions;

public class UserIsNotActiveException extends Exception {
    public UserIsNotActiveException() {
        super("Client is not active");
    }
}

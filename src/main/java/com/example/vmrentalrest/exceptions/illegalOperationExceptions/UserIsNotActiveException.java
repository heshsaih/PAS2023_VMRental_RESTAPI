package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class UserIsNotActiveException extends IllegalOperationException {
    public UserIsNotActiveException() {
        super("Client is not active");
    }
}

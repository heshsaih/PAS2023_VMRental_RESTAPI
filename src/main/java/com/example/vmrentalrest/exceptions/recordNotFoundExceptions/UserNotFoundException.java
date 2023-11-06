package com.example.vmrentalrest.exceptions.recordNotFoundExceptions;

public class UserNotFoundException extends RecordNotFoundException{
    public UserNotFoundException() {
        super("User not found");
    }
}

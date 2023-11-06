package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class ClientHasTooManyRentsException extends IllegalOperationException{
    public ClientHasTooManyRentsException() {
        super("Client has too many rents");
    }
}

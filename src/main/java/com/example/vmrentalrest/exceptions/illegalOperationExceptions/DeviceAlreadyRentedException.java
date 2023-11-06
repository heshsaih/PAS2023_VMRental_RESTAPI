package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class DeviceAlreadyRentedException extends IllegalOperationException{
    public DeviceAlreadyRentedException() {
        super("Device is already rented");
    }
}

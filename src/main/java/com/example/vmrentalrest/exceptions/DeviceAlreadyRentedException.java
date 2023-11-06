package com.example.vmrentalrest.exceptions;

public class DeviceAlreadyRentedException extends Exception{
    public DeviceAlreadyRentedException() {
        super("Device is already rented");
    }
}

package com.example.vmrentalrest.exceptions.recordNotFoundExceptions;

public class VirtualDeviceNotFoundException extends RecordNotFoundException{
    public VirtualDeviceNotFoundException() {
        super("Virtual device not found");
    }
}

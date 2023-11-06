package com.example.nbd.exceptions.recordNotFoundExceptions;

public class VirtualDeviceNotFoundException extends RecordNotFoundException{
    public VirtualDeviceNotFoundException() {
        super("Virtual device not found");
    }
}

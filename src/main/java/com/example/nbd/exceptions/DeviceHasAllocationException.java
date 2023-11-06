package com.example.nbd.exceptions;

public class DeviceHasAllocationException extends Exception {
    public DeviceHasAllocationException() {
        super("Device has allocation");
    }
}

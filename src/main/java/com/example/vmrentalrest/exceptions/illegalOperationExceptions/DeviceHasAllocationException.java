package com.example.vmrentalrest.exceptions.illegalOperationExceptions;

public class DeviceHasAllocationException extends IllegalOperationException {
    public DeviceHasAllocationException() {
        super("Device has allocation");
    }
}

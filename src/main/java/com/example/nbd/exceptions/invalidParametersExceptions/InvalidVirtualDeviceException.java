package com.example.nbd.exceptions.invalidParametersExceptions;

public class InvalidVirtualDeviceException extends InvalidParametersException {
    public InvalidVirtualDeviceException() {
        super("Invalid virtual device");
    }
}

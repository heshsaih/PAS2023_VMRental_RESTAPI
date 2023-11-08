package com.example.vmrentalrest.exceptions.invalidParametersExceptions;

public class UnknownVirtualDeviceTypeException extends InvalidParametersException {

        public UnknownVirtualDeviceTypeException() {
            super("Unknown virtual device type");
        }
}

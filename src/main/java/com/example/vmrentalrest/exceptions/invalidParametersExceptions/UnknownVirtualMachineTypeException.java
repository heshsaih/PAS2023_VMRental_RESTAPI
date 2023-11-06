package com.example.vmrentalrest.exceptions.invalidParametersExceptions;

public class UnknownVirtualMachineTypeException extends InvalidParametersException {

        public UnknownVirtualMachineTypeException() {
            super("Unknown virtual machine type");
        }
}

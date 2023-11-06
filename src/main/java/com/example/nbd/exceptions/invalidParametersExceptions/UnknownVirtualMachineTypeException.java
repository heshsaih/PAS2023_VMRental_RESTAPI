package com.example.nbd.exceptions.invalidParametersExceptions;

public class UnknownVirtualMachineTypeException extends InvalidParametersException {

        public UnknownVirtualMachineTypeException() {
            super("Unknown virtual machine type");
        }
}

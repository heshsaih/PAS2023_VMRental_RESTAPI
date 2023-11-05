package com.example.nbd.exceptions;

public class UnknownVirtualMachineTypeException extends UnknownEnumException{

        public UnknownVirtualMachineTypeException() {
            super("Unknown virtual machine type");
        }
}

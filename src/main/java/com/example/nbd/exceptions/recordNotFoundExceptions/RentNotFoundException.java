package com.example.nbd.exceptions.recordNotFoundExceptions;

public class RentNotFoundException extends RecordNotFoundException{
    public RentNotFoundException() {
        super("Rent not found");
    }
}

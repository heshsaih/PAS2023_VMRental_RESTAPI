package com.example.vmrentalrest.exceptions;

import com.example.vmrentalrest.exceptions.illegalOperationExceptions.IllegalOperationException;
import com.example.vmrentalrest.exceptions.invalidParametersExceptions.InvalidParametersException;
import com.example.vmrentalrest.exceptions.recordNotFoundExceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalOperationException(IllegalOperationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleInvalidParametersException(InvalidParametersException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}

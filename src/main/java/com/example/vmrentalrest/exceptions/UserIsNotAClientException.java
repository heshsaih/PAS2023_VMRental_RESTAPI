package com.example.vmrentalrest.exceptions;

public class UserIsNotAClientException extends Exception {

        public UserIsNotAClientException () {
            super("User is not a client");
        }
}

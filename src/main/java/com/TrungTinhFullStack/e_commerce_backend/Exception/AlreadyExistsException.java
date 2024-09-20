package com.TrungTinhFullStack.e_commerce_backend.Exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}

package com.TrungTinhFullStack.e_commerce_backend.Exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

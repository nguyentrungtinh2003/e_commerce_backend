package com.TrungTinhFullStack.e_commerce_backend.Exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
}

package com.sumhr.api.error;

public class CustomResourceNotFoundException extends RuntimeException {

    private String message;

    public CustomResourceNotFoundException(String message) {
        this.message = message;
    }

}

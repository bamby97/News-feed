package com.example.newsfeed.exceptions;

public class NoUserFoundException extends RuntimeException{

    public NoUserFoundException() {
        super("The user you provided does not exist");
    }
    public NoUserFoundException(String message) {
        super(message);
    }
}

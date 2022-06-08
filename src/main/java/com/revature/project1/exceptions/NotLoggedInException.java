package com.revature.project1.exceptions;

import com.revature.project1.annotations.Author;

@Author(authorName = "Leo Schaffner",
        description = "Custom exception to be thrown when a function requiring a login is called without a login")
public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {}
    public NotLoggedInException(String message) { super(message); }
}

package com.revature.project1.exceptions;

import com.revature.project1.annotations.Author;

@Author(authorName = "Leo Schaffner",
        description = "Custom exception to be thrown when a login is attempted with invalid credentials")
public class LoginFailException extends RuntimeException {
    public LoginFailException() {}
    public LoginFailException(String message) { super(message); }
}

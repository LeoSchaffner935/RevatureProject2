package com.revature.project1.controller;

import com.revature.project1.annotations.Author;
import com.revature.project1.exceptions.LoginFailException;
import com.revature.project1.model.User;
import com.revature.project1.services.CartService;
import com.revature.project1.services.UserService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Author(authorName = "Leo Schaffner",
        description = "Rest Controller for register(), login(), and logout()")
@RestController
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;

    @Timed(value = "regsiter.time")
    @PostMapping("/register") // localhost:8080/register
    public ResponseEntity<String> register(@RequestBody User u) {
        LOGGER.info("register() started execution");
        ResponseEntity<String> responseEntity;

        if(userService.isUserExistsByCombo(u.getEmail(), u.getPassword())) { // Check if user exists before adding
            responseEntity = new ResponseEntity<String>
                    ("Cannot add as email " + u.getEmail() + " already exists", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot add as email " + u.getEmail() + " already exists");
        }
        else {
            if (userService.register(u, cartService.createCart())) { // Returns false if email is invalid or already exists
                responseEntity = new ResponseEntity<String>
                        (u + " added successfully.\nLogging in " + u.getEmail(), HttpStatus.OK);
                LOGGER.info(u + " added successfully.\nLogging in " + u.getEmail());

                userService.login(u.getEmail(), u.getPassword()); // Login user after registering
            }
            else {
                responseEntity = new ResponseEntity<String>
                        ("Cannot add user as email is invalid or already exists", HttpStatus.NOT_ACCEPTABLE);
                LOGGER.error("Cannot add user as email is invalid or already exists");
            }
        }
        return responseEntity;
    }

    @Timed(value = "login.time")
    @Counted(value = "login.fail.counter", recordFailuresOnly = true)
    @GetMapping("/login/{email}/{password}") // localhost:8080/login/{email}/{password}
    public ResponseEntity<User> login(@PathVariable("email") String email, @PathVariable("password") String password) { // Create session, Get a user and their cart from "users" table in database by supplying login credentials
        LOGGER.info("login() started execution");

        if (userService.isUserExistsByCombo(email, password)) { // Check is user exists before fetching
            LOGGER.info(userService.login(email, password) + " logged in successfully");
            return new ResponseEntity<User> (userService.login(email, password), HttpStatus.OK);
        }

        LOGGER.error("Login credentials invalid");
        throw new LoginFailException("Login Failed: Invalid Credentials"); // Throws custom exception with HTTP 500 code as method return type is incompatible
    }

    @Timed(value = "logout.time")
    @GetMapping("/logout") // localhost:8080/logout
    public ResponseEntity<String> logout() { // Logs out user
        LOGGER.info("logout() started execution");

        userService.logout();
        LOGGER.info("Logout Successful!");
        return new ResponseEntity<String>("Logout Successful!", HttpStatus.OK);
    }
}

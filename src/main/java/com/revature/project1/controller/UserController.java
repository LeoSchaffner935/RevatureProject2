package com.revature.project1.controller;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.User;
import com.revature.project1.services.UserService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "User Rest Controller")
@RestController
@RequestMapping("users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Timed(value = "getallusers.time")
    @GetMapping() // localhost:8080/users
    public ResponseEntity<List<User>> getUsers() { // Get all users from "users" table in database
        LOGGER.info("getUsers() started execution");

        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }

    @Timed(value = "deleteuser.time")
    @DeleteMapping("{userId}") // localhost:8080/users/{userId}
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) { // Delete a single user from "users" table in database
        LOGGER.info("deleteUser() started execution");
        ResponseEntity<String> responseEntity;

        if(!(userService.isUserExistsById(userId))) { // Check if user doesn't exist before deleting
            responseEntity = new ResponseEntity<String>
                    ("Cannot delete as id " + userId + " doesn't exist", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot delete as id " + userId + " doesn't exist");
        }
        else {
            userService.deleteUser(userId);
            responseEntity = new ResponseEntity<String>
                    ("Id " + userId + " deleted successfully", HttpStatus.OK);
            LOGGER.info("Id " + userId + " deleted successfully");
        }
        return responseEntity;
    }

    @Timed(value = "updateuser.time")
    @PutMapping // localhost:8080/users/{userId}
    public ResponseEntity<String> updateUser(@RequestBody User u) { // Update a single user from "users" table in database
        LOGGER.info("updateUser() started execution");
        ResponseEntity<String> responseEntity;

        if(!(userService.isUserExistsById(u.getUserId()))) { // Check if user doesn't exist before updating
            responseEntity = new ResponseEntity<String>
                    ("Cannot update as id " + u.getUserId() + " doesn't exist", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot update as id " + u.getUserId() + " doesn't exist");
        }
        else {
            if (userService.updateUser(u)) { // Returns false if email is invalid
                responseEntity = new ResponseEntity<String>
                        (u + " updated successfully", HttpStatus.OK);
                LOGGER.info(u + " updated successfully");
            }
            else {
                responseEntity = new ResponseEntity<String>
                        (" Cannot update user id " + u.getUserId() + " as email is invalid or already exists", HttpStatus.NOT_ACCEPTABLE);
                LOGGER.error("Cannot update user id " + u.getUserId() + " as email is invalid or already exists");
            }
        }
        return responseEntity;
    }

    @Timed(value = "getsingleuser.time")
    @GetMapping("{userid}") // localhost:8080/users/{userid}
    public ResponseEntity<User> getUser(@PathVariable("userid") int userId) { // Get a single user from "users" table in database
        LOGGER.info("getUser() started execution");
        ResponseEntity<User> responseEntity;

        if (userService.isUserExistsById(userId)) {
            responseEntity = new ResponseEntity<User>
                    (userService.getUser(userId), HttpStatus.OK);
            LOGGER.info(userService.getUser(userId) + " retrieved successfully");
        }
        else {
            responseEntity = new ResponseEntity<User>
                    (new User(), HttpStatus.NO_CONTENT);
            LOGGER.info("User does not exist");
        }
        return responseEntity;
    }
}

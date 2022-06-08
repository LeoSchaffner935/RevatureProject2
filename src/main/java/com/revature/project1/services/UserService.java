package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Cart;
import com.revature.project1.model.User;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Interface methods to be implemented in UserServiceImpl and called by UserController")
public interface UserService {
    List<User> getUsers(); // Get all users from "users" table in database
    boolean register(User u, Cart c); // Add a single user to "users" table in database, assigns user newly created cart returned from CartService.createCart()
    void deleteUser(int userId); // Delete a single user from "users" table in database
    boolean updateUser(User u); // Update a single user from "users" table ein database
    User getUser(int userId); // // Get a single user from "users" table in database
    User login(String email, String password); // Creates session, Get a single user from "users" table in database using login credentials
    void logout(); // Logout user
    boolean isUserExistsById(int userId); // Check if user with id = userId exists in "users" table in database
    boolean isUserExistsByCombo(String email, String password); // Check if user with matching login credentials exists in "users" table in database
}

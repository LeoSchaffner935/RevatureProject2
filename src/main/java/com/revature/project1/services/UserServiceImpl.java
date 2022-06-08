package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.dao.UserDAO;
import com.revature.project1.model.Cart;
import com.revature.project1.model.User;
import com.revature.project1.utilities.CheckValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Implements methods in UserService interface")
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    UserDAO userDAO;
    @Autowired
    CheckValue checkValue;
    @Autowired
    HttpServletRequest req;

    @Override
    public List<User> getUsers() { // Get all users from "users" table in database
        LOGGER.info("Executing getUsers() service");

        return userDAO.findAll();
    }

    @Override
    public boolean register(User u, Cart c) { // Add a single user to "users" table in database, assigns user newly created cart returned from CartService.createCart()
        LOGGER.info("Executing addUser() service");

        if (!checkValue.checkOWASPEmail(u.getEmail())) { // Check if email is valid before adding
            LOGGER.warn("Failed to save user in service since email is invalid");
            return false;
        }
        else if (userDAO.existsByEmail(u.getEmail())) { // Checks for duplicate emails, in UserDAO
            LOGGER.warn("Failed to save user in service since email is already exists");
            return false;
        }

        u.setCart(c);
        userDAO.save(u);
        LOGGER.info("User added in service");
        return true;
    }

    @Override
    public void deleteUser(int userId) { // Delete a single user from "users" table in database
        LOGGER.info("Executing deleteUser() service");

        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("currentUser") != null) {
            User sessionUser = (User) session.getAttribute("currentUser");
            // If a logged-in user is deleted, logout
            if(sessionUser.getUserId() == userId) logout();
        }

        userDAO.deleteById(userId);
        LOGGER.info("User deleted in service");
    }

    @Override
    public boolean updateUser(User u) { // Update a single user from "users" table ein database
        LOGGER.info("Executing updateItem() service");

        if (checkValue.checkOWASPEmail(u.getEmail())) { // Check if email is valid before updating
            LOGGER.warn("Failed to save user in service since email is invalid");
            return false;
        }
        else if (userDAO.existsByEmail(u.getEmail())) { // Checks for duplicate emails, in UserDAO
            LOGGER.warn("Failed to save user in service since email is already exists");
            return false;
        }
        userDAO.save(u);
        LOGGER.info("User updated in service");

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("currentUser") == null) return true;

        User sessionUser = (User) session.getAttribute("currentUser");
        // If a logged-in user is updated, update the information in the session
        if(sessionUser.getUserId() == u.getUserId()) session.setAttribute("currentUser", u);

        return true;
    }

    @Override
    public User getUser(int userId) { // Get a single user from "users" table in database
        LOGGER.info("Executing getUser() service");

        return userDAO.getById(userId);
    }

    @Override
    public User login(String email, String password) { // Create session, Get a single user from "users" table in database using login credentials
        LOGGER.info("Executing login() service");

        User u = userDAO.findByEmailAndPassword(email, password).get(0); // Custom method in userDAO
        HttpSession session = req.getSession();
        session.setAttribute("currentUser", u);

        LOGGER.info("Logged in as " + email);
        return u;
    }

    @Override
    public void logout() {
        LOGGER.info("Executing logout() service");

        HttpSession session = req.getSession(false);
        if(session == null) return; // No one was logged in
        session.invalidate();
    }

    @Override
    public boolean isUserExistsById(int userId) { // Check if user with id = userId exists in "users" table in database
        LOGGER.info("Executing isUserExists() service");

        return userDAO.existsById(userId);
    }

    @Override
    public boolean isUserExistsByCombo(String email, String password) { // Check if user with matching login credentials exists in "users" table in database
        LOGGER.info("Executing isUserExists() service");

        return userDAO.existsByEmailAndPassword(email, password); // Custom method in UserDAO
    }
}

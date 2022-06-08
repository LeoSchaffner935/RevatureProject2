package com.revature.project1.dao;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "User Data Access Object, JpaRepository methods called in UserService; Some app functionality requires users to be checked with login credentials rather than auto-assigned id, extra methods follow JPA naming conventions")
public interface UserDAO extends JpaRepository<User, Integer> {
    List<User> findByEmailAndPassword(String email, String password); // If user exists, retrieves information from "users" table in database
    boolean existsByEmailAndPassword(String email, String password); // Checks if user with matching credentials exists
    boolean existsByEmail(String email); // Used in register(), only checks for matching email
}

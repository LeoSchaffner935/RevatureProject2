package com.revature.project1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.project1.annotations.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Author(authorName = "Leo Schaffner",
        description = "User model class, creates 'users' table in database")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId; // Auto-generated primary key of "users" table
    private String email; // User email, used for login
    private String password; // User password, used for login

    @OneToOne(cascade = CascadeType.ALL) // One-to-one relationship between Users and Carts
    @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    private Cart cart; // Foreign key "cart_id" references primary key "cart_id" in "carts" table
}

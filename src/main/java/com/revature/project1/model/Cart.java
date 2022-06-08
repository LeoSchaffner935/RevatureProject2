package com.revature.project1.model;

import com.revature.project1.annotations.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Cart model class, creates 'carts' table in database")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int cartId; // Auto-generated primary key of "carts" table
    private double subtotal; // Subtotal of cart, sum of price of items in cart

    @ManyToMany
    @JoinTable(name = "cartlist",
            joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId"),
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"))
    private List<Item> itemList; // Creates a join table named "cartlist" which references "carts" and "items" tables; "cart_id" and "item_id" are respective foreign keys
}

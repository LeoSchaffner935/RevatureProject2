package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Cart;
import com.revature.project1.model.Order;

@Author(authorName = "Leo Schaffner",
        description = "Interface methods to be implemented in CartServiceImpl and called by ShoppingController and LoginController")
public interface CartService {
    Cart createCart(); // Creates an empty cart to be assigned to a new user
    boolean addItemToCart(int itemId); // Adds a single item with id = itemId to cart c
    boolean placeOrder(Order o); // Empties cart, reduces item qoh, and adds order to "orders" table in database
}

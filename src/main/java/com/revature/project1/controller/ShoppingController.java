package com.revature.project1.controller;

import com.revature.project1.annotations.Author;
import com.revature.project1.annotations.Authenticate;
import com.revature.project1.model.Order;
import com.revature.project1.services.CartService;
import com.revature.project1.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Author(authorName = "Leo Schaffner",
        description = "Rest Controller for addItemToCart() and placeOrder()")
@RestController
public class ShoppingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);
    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;

    @Authenticate
    @PostMapping("/additemtocart/{itemid}") // localhost:8080/additemtocart/{itemid}
    public ResponseEntity<String> addItemToCart(@PathVariable("itemid") int itemId) { // Add a single item to a user's cart in "carts" table in database
        LOGGER.info("addItemToCart() started execution");
        ResponseEntity<String> responseEntity;

        if(!(itemService.isItemExists(itemId))) { // Check if item doesn't exist before adding
            responseEntity = new ResponseEntity<String>
                    ("Cannot add to cart as item id " + itemId + " doesn't exist", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot add to cart as item id " + itemId + " doesn't exist");
        }
        else {
            if (cartService.addItemToCart(itemId)) { // Returns false if item price or qoh is negative
                responseEntity = new ResponseEntity<String>
                        ("Item added to cart successfully", HttpStatus.OK);
                LOGGER.info("Item added to cart successfully");
            }
            else {
                responseEntity = new ResponseEntity<String>
                        ("Cannot add item id " + itemId + " to cart as item is out of stock", HttpStatus.NOT_ACCEPTABLE);
                LOGGER.error("Cannot add user item id " + itemId + " to cart as item is out of stock");
            }
        }
        return responseEntity;
    }

    @Authenticate
    @PostMapping("/placeorder") // localhost:8080/placeorder
    public ResponseEntity<String> placeOrder(@RequestBody Order o) { // Add a single order to "orders" table in database
        LOGGER.info("placeOrder() started execution");
        ResponseEntity<String> responseEntity;

        if (cartService.placeOrder(o)) { // Returns false cart is empty before ordering
            responseEntity = new ResponseEntity<String>
                    ("Order successfully placed! Cart emptied", HttpStatus.OK);
            LOGGER.info("Order successfully placed! Cart emptied");
        }
        else {
            responseEntity = new ResponseEntity<String>
                    ("Cannot place order as cart is empty", HttpStatus.NOT_ACCEPTABLE);
            LOGGER.error("Cannot place order as cart is empty");
        }
        return responseEntity;
    }
}

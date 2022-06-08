package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Item;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Interface methods to be implemented in ItemServiceImpl and called by ItemController and ShoppingController")
public interface ItemService {
    List<Item> getItems(); // Get all items from "items" table in database
    boolean addItem(Item i); // Add a single item to "items" table in database
    void deleteItem(int itemId); // Delete a single item from "items" table in database
    boolean updateItem(Item i); // Update a single item from "items" table in database
    Item getItem(int itemId); // Get a single item from "items" table in database
    boolean isItemExists(int itemId); // Check if an item exists in "items" table in database
    boolean isItemExistsByName(String itemName); // Check if an item with the same name exists in "items" table in database
}

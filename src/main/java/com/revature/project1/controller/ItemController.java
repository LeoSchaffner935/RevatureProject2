package com.revature.project1.controller;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Item;
import com.revature.project1.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Item Rest Controller")
@RestController
@RequestMapping("items")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    ItemService itemService;

    @GetMapping() // localhost:8080/items
    public ResponseEntity<List<Item>> getItems() { // Get all items from "items" table in database
        LOGGER.info("getItems() started execution");

        return new ResponseEntity<List<Item>>(itemService.getItems(), HttpStatus.OK);
    }

    @PostMapping // localhost:8080/items
    public ResponseEntity<String> addItem(@RequestBody Item i) { // Add a single item to "items" table in database
        LOGGER.info("addItem() started execution");
        ResponseEntity<String> responseEntity;

        if(itemService.isItemExists(i.getItemId())) { // Check if item exists before adding
            responseEntity = new ResponseEntity<String>
                    ("Cannot add as id " + i.getItemId() + " already exists", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot add as id " + i.getItemId() + " already exists");
        }
        else if(itemService.isItemExistsByName(i.getItemName())) {
            responseEntity = new ResponseEntity<String>
                    ("Cannot add as name " + i.getItemName() + " already exists", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot add as name " + i.getItemName() + " already exists");
        }
        else {
            if (itemService.addItem(i)) { // Returns false if price or qoh is negative
                responseEntity = new ResponseEntity<String>
                        (i + " added successfully", HttpStatus.OK);
                LOGGER.info(i + " added successfully");
            }
            else {
                responseEntity = new ResponseEntity<String>
                        ("Cannot add item as price or qoh is negative", HttpStatus.NOT_ACCEPTABLE);
                LOGGER.error("Cannot add item as price or qoh is negative");
            }
        }
        return responseEntity;
    }

    @DeleteMapping("{itemId}") // localhost:8080/items/{itemId}
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") int itemId) { // Delete a single item from "items" table in database
        LOGGER.info("deleteItem() started execution");
        ResponseEntity<String> responseEntity;

        if(!(itemService.isItemExists(itemId))) { // Check if item doesn't exist before deleting
            responseEntity = new ResponseEntity<String>
                    ("Cannot delete as id " + itemId + " doesn't exist", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot delete as id " + itemId + " doesn't exist");
        }
        else {
            itemService.deleteItem(itemId);
            responseEntity = new ResponseEntity<String>
                    ("Id " + itemId + " deleted successfully", HttpStatus.OK);
            LOGGER.info("Id " + itemId + " deleted successfully");
        }
        return responseEntity;
    }

    @PutMapping // localhost:8080/items/{itemId}
    public ResponseEntity<String> updateItem(@RequestBody Item i) { // Updates a single item in "items" table in database
        LOGGER.info("updateItem() started execution");
        ResponseEntity<String> responseEntity;

        if(!(itemService.isItemExists(i.getItemId()))) { // Check if item doesn't exist before updating
            responseEntity = new ResponseEntity<String>
                    ("Cannot update as id " + i.getItemId() + " doesn't exist", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot update as id " + i.getItemId() + " doesn't exist");
        }
        else if(itemService.isItemExistsByName(i.getItemName())) {
            responseEntity = new ResponseEntity<String>
                    ("Cannot add as name " + i.getItemName() + " already exists", HttpStatus.CONFLICT);
            LOGGER.warn("Cannot add as name " + i.getItemName() + " already exists");
        }
        else {
            if (itemService.updateItem(i)) { // Returns false if price or qoh is negative
                responseEntity = new ResponseEntity<String>
                        (i + " updated successfully", HttpStatus.OK);
                LOGGER.info(i + " updated successfully");
            }
            else {
                responseEntity = new ResponseEntity<String>
                        (" Cannot update item id " + i.getItemId() + " as price or qoh is negative", HttpStatus.NOT_ACCEPTABLE);
                LOGGER.error("Cannot update item id " + i.getItemId() + " as price or qoh is negative");
            }
        }
        return responseEntity;
    }

    @GetMapping("{itemId}") // localhost:8080/items/{itemId}
    public ResponseEntity<Item> getProduct(@PathVariable("itemId") int itemId) { // Get a single item from "items" table in database
        LOGGER.info("getItem() started execution");
        ResponseEntity<Item> responseEntity;

        if (itemService.isItemExists(itemId)) { // Check if item doesn't exist before fetching
            responseEntity = new ResponseEntity<Item>
                    (itemService.getItem(itemId), HttpStatus.OK);
            LOGGER.info(itemService.getItem(itemId) + " retrieved successfully");
        }
        else {
            responseEntity = new ResponseEntity<Item>
                    (new Item(), HttpStatus.NO_CONTENT);
            LOGGER.warn("Item does not exist");
        }
        return responseEntity;
    }
}

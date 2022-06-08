package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.dao.ItemDAO;
import com.revature.project1.model.Item;
import com.revature.project1.utilities.CheckValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Implements methods in ItemService interface")
@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    CheckValue checkValue;

    @Override
    public List<Item> getItems() { // Get all items from "items" table in database
        LOGGER.info("Executing getItems() service");

        return itemDAO.findAll();
    }

    @Override
    public boolean addItem(Item i) { // Add a single item to "items" table in database
        LOGGER.info("Executing addItem() service");

        if (checkValue.checkNegativeValue((int) i.getPrice()) && checkValue.checkNegativeValue(i.getQoh())) { // Check if price or qoh is negative before adding
            itemDAO.save(i);
            LOGGER.info("Item added in service");
            return true;
        }
        LOGGER.warn("Failed to save item in service since price or qoh is negative");
        return false;
    }

    @Override
    public void deleteItem(int itemId) { // Delete a single item from "items" table in database
        LOGGER.info("Executing deleteItem() service");

        itemDAO.deleteById(itemId);
        LOGGER.info("Item deleted in service");
    }

    @Override
    public boolean updateItem(Item i) { // Update a single item from "items" table in database
        LOGGER.info("Executing updateItem() service");

        if (checkValue.checkNegativeValue((int) i.getPrice()) && checkValue.checkNegativeValue(i.getQoh())) { // Check if price or qoh is negative before updating
            itemDAO.save(i);
            LOGGER.info("Item updated in service");
            return true;
        }
        LOGGER.warn("Failed to save item in service since price or qoh is negative");
        return false;
    }

    @Override
    public Item getItem(int itemId) { // Get a single item from "items" table in database
        LOGGER.info("Executing getItem() service");

        return itemDAO.getById(itemId);
    }

    @Override
    public boolean isItemExists(int itemId) { // Check if an item exists in "items" table in database
        LOGGER.info("Executing isItemExists() service");

        return itemDAO.existsById(itemId);
    }

    @Override
    public boolean isItemExistsByName(String itemName) { // Check if an item with the same name exists in "items" table in database
        LOGGER.info("Executing isItemExists() service");

        return itemDAO.existsByItemName(itemName); // Custom method in ItemDAO
    }
}

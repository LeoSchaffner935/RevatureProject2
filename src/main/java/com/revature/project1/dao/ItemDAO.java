package com.revature.project1.dao;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

@Author(authorName = "Leo Schaffner",
        description = "Item Data Access Object, JpaRepository methods called in ItemService")
public interface ItemDAO extends JpaRepository<Item, Integer> {
    boolean existsByItemName(String itemName); // Checks for duplicate items
}
package com.revature.project1.model;

import com.revature.project1.annotations.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Item model class, creates 'items' table in database")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int itemId; // Auto-generated primary key of "items" table
    private String itemName; // Item name
    private int qoh; // Item quantity
    private double price; // Item price
}

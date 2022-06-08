package com.revature.project1.model;

import com.revature.project1.annotations.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Author(authorName = "Leo Schaffner",
        description = "Order model class, creates 'orders' table in database")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int orderId; // Auto-generated primary key of "orders" table
    private int userId; // Id of user who placed order
    private double total; // Order total including tax
    private String address; // Shipping address
    private String city;
    private String state;
    private String zip;

    @ManyToMany
    @JoinTable(name = "orderlist",
            joinColumns = @JoinColumn(name = "orderId", referencedColumnName = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "itemId", referencedColumnName = "itemId"))
    private List<Item> itemList; // Creates a join table named "orderlist" which references "orders" and "items" tables; "order_id" and "item_id" are respective foreign keys
}

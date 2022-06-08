package com.revature.project1.dao;

import com.revature.project1.annotations.Author;
import com.revature.project1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Author(authorName = "Leo Schaffner",
        description = "Order Data Access Object, JpaRepository methods called in CartService")
public interface OrderDAO extends JpaRepository<Order, Integer> {}

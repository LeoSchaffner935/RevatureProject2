package com.revature.project1.services;

import com.revature.project1.annotations.Author;
import com.revature.project1.dao.CartDAO;
import com.revature.project1.dao.OrderDAO;
import com.revature.project1.model.Cart;
import com.revature.project1.model.Item;
import com.revature.project1.model.Order;
import com.revature.project1.model.User;
import com.revature.project1.utilities.CheckValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Author(authorName = "Leo Schaffner",
        description = "Implements methods in CartService interface")
@Service
public class CartServiceImpl implements CartService{
    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    CartDAO cartDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ItemService itemService;
    @Autowired
    CheckValue checkValue;
    @Autowired
    HttpServletRequest req;

    @Override
    public Cart createCart() { // Creates an empty cart to be assigned to a new user
        LOGGER.info("Executing createCart() service");

        Cart c = new Cart();
        c.setItemList(new ArrayList<Item>());
        c.setSubtotal(0);
        cartDAO.save(c);
        LOGGER.info("Cart created for new user");
        return c;
    }

    @Override
    public boolean addItemToCart(int itemId) { // Adds a single item with id = itemId to cart c
        LOGGER.info("Executing addItemToCart() service");

        if(checkValue.checkStock(itemService.getItem(itemId).getQoh())) { // Check if item is out of stock before adding to cart
            HttpSession session = req.getSession(false);
            User sessionUser = (User) session.getAttribute("currentUser");

            Cart c = sessionUser.getCart();
            List<Item> temp = c.getItemList();
            temp.add(itemService.getItem(itemId));
            c.setItemList(temp);
            c.setSubtotal(c.getSubtotal()+itemService.getItem(itemId).getPrice()); // Increases cart subtotal
            cartDAO.save(c);
            sessionUser.setCart(c); // Update logged-in user in addition to table
            LOGGER.info("Item added to cart in service");
            return true;
        }
        LOGGER.warn("Failed to add item to cart in service since item is out of stock");
        return false;
    }

    @Override
    public boolean placeOrder(Order o) { // Empties cart, reduces item qoh, and adds order to "orders" table in database
        LOGGER.info("Executing placeOrder() service");
        HttpSession session = req.getSession(false);
        User sessionUser = (User) session.getAttribute("currentUser");

        if (sessionUser.getCart().getItemList().size() == 0) { // Check if cart is empty before ordering
            LOGGER.warn("Cart is empty, cannot place order");
            return false;
        }

        Cart c = sessionUser.getCart();

        // Set remaining order fields
        o.setUserId(sessionUser.getUserId());
        List<Item> temp = new ArrayList<Item>(c.getItemList());
        o.setItemList(temp);
        o.setTotal(c.getSubtotal()+0.06625*c.getSubtotal()); // NJ sales tax is 6.625%
        orderDAO.save(o);

        // Empty user cart once order is placed
        c.setItemList(new ArrayList<Item>());
        c.setSubtotal(0);
        cartDAO.save(c);
        sessionUser.setCart(c); // Update logged-in user in addition to table

        // Decrease stock of items ordered
        Set<Integer> repeats = new TreeSet<>(); // Contains ids of duplicate items
        for (Item i: temp) {
            if (repeats.contains(i.getItemId())) break;

            int count = 1;
            if (i.getQoh() == 1) LOGGER.info("Item " + i.getItemId() + " needs to be restocked"); // If last item is purchased

            for (int j=temp.indexOf(i)+1; j<temp.size(); j++) { // Checks for duplicates
                if (i.getItemId() == temp.get(j).getItemId()) {
                    repeats.add(i.getItemId());
                    count++;
                }
            }

            i.setQoh(i.getQoh()-count); // Decreases stock depending on how many of the item are in cart
            itemService.updateItem(i);
        }

        LOGGER.info("Order placed in service");
        return true;
    }
}

package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.OrderDao;
import com.meesmb.iprwc.model.ProductsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderDao orderDao;

    @GetMapping("/order")
    public ResponseEntity<List<ProductsOrder>> getOrdersFromAccountInToken(@RequestHeader(name = "Authorization") String token) {
        return orderDao.getAccountsOrders(token);
    }

    @PostMapping("/order")
    public ResponseEntity<ProductsOrder> convertToOrder(@RequestHeader(name = "Authorization") String token) {
        return orderDao.convertAccountShoppingCartToOrder(token);
    }

    @DeleteMapping("/order")
    public ResponseEntity<String> deleteOrder(@RequestBody ProductsOrder order) {
        return orderDao.deleteOrder(order);
    }
}

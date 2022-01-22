package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.OrderDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.ProductsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderDao orderDao;

    @GetMapping("/order")
    public HTTPResponse<List<ProductsOrder>> getOrders(@RequestHeader(name = "Authorization") String token) {
        return orderDao.getAccountsOrders(token);
    }

    @PostMapping("/order")
    public HTTPResponse<ProductsOrder> convertToOrder(@RequestHeader(name = "Authorization") String token) {
        return orderDao.convertAccountShoppingCartToOrder(token);
    }

    @DeleteMapping("/order")
    public HTTPResponse<String> deleteOrder(@RequestHeader(name = "Authorization") String token, @RequestBody ProductsOrder order) {
        return orderDao.deleteOrder(order);
    }
}

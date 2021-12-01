package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.ShoppingCartDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController {
    @Autowired
    ShoppingCartDao dao;

    @CrossOrigin(origins = "*")
    @GetMapping("/cart")
    public HTTPResponse<ShoppingCart> getShoppingCart(@RequestParam(name = "account_id", defaultValue = "") String accountId) {
        if (accountId.equals("")) return HTTPResponse.<ShoppingCart>returnFailure("no account_id");
        return dao.getShoppingCartByAccountId(accountId);
    }

}

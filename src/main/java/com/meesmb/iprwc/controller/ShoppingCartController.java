package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.ShoppingCartDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtTokenUtil;
import com.meesmb.iprwc.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ShoppingCartController {
    @Autowired
    ShoppingCartDao dao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/cart")
    public HTTPResponse<ShoppingCart> getShoppingCart(@RequestHeader(name = "Authorization") String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return dao.getShoppingCartByAccountId(email);
    }

    @PostMapping("/cart")
    public HTTPResponse<ShoppingCart> postShoppingCart(@RequestHeader(name = "Authorization") String token, @RequestBody ShoppingCart cart) {
        String email = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return dao.setShoppingCart(cart, email);
    }

}

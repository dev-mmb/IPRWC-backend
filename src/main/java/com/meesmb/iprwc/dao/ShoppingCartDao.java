package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.ShoppingCart;
import com.meesmb.iprwc.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShoppingCartDao {
    @Autowired
    ShoppingCartRepository repository;

    public HTTPResponse<ShoppingCart> getShoppingCartByAccountId(String accountId) {
        Optional<ShoppingCart> data = repository.findByAccount_id(accountId);
        if (data.isEmpty()) return HTTPResponse.<ShoppingCart>returnFailure("account not found");
        return HTTPResponse.<ShoppingCart>returnSuccess(data.get());
    }
}

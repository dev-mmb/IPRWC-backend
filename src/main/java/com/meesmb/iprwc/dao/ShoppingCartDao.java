package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.model.ShoppingCart;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.ProductRepository;
import com.meesmb.iprwc.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;

@Component
public class ShoppingCartDao {
    @Autowired
    ShoppingCartRepository repository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AccountRepository accountRepository;

    public HTTPResponse<ShoppingCart> getShoppingCartByAccountId(String AccountEmail) {
        Account account = accountRepository.findByEmail(AccountEmail);
        if (account == null) return HTTPResponse.<ShoppingCart>returnFailure("account not found");
        return HTTPResponse.<ShoppingCart>returnSuccess(account.getShoppingCart());
    }

    public HTTPResponse<ShoppingCart> setShoppingCart(String[] productIds, String accountEmail) {
        Account account = accountRepository.findByEmail(accountEmail);

        if (account == null) return HTTPResponse.<ShoppingCart>returnFailure("account not found");

        if (account.getShoppingCart() == null) {
            ShoppingCart cart = new ShoppingCart(new Product[0]);
            repository.save(cart);
            account.setShoppingCart(cart);
        }

        HashSet<Product> productData = new HashSet<>();
        for (String productId : productIds) {
            Optional<Product> p = productRepository.findById(productId);
            p.ifPresent(productData::add);
        }
        account.getShoppingCart().setProducts(productData);
        accountRepository.save(account);
        return HTTPResponse.returnSuccess(account.getShoppingCart());
    }
}

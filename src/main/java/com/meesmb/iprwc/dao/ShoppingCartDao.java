package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.ShoppingCart;
import com.meesmb.iprwc.model.ShoppingCartProduct;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.ShoppingCartProductRepository;
import com.meesmb.iprwc.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class ShoppingCartDao {
    @Autowired
    ShoppingCartRepository repository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;

    public HTTPResponse<ShoppingCart> getShoppingCartByAccountId(String AccountEmail) {
        Account account = accountRepository.findByEmail(AccountEmail);
        if (account == null) return HTTPResponse.<ShoppingCart>returnFailure("account not found");
        return HTTPResponse.<ShoppingCart>returnSuccess(account.getShoppingCart());
    }

    public HTTPResponse<ShoppingCart> setShoppingCart(ShoppingCart cart, String accountEmail) {
        Account account = accountRepository.findByEmail(accountEmail);

        if (account == null) return HTTPResponse.<ShoppingCart>returnFailure("account not found");

        if (account.getShoppingCart() == null) {
            ShoppingCart c = new ShoppingCart(new ShoppingCartProduct[0]);
            repository.save(c);
            account.setShoppingCart(c);
        }
        // remove old products
        emptyShoppingCart(account);
        // put into db if not exist yet
        Set<ShoppingCartProduct> n = createAllShoppingCartproducts(cart.getProducts());
        // save them into the account
        account.getShoppingCart().setProducts(n);

        this.repository.save(account.getShoppingCart());
        accountRepository.save(account);
        return HTTPResponse.returnSuccess(account.getShoppingCart());
    }

    Set<ShoppingCartProduct> createAllShoppingCartproducts(Set<ShoppingCartProduct> products) {
        for (ShoppingCartProduct p : products) {
            if (p.getId() == null) {
                p.setId(UUID.randomUUID().toString());
            }
            this.shoppingCartProductRepository.save(p);
        }
        return products;
    }
    // delete all products from a shopping cart
    void emptyShoppingCart(Account account) {
        Set<ShoppingCartProduct> old = account.getShoppingCart().getProducts();
        account.getShoppingCart().setProducts(new HashSet<>());
        this.repository.save(account.getShoppingCart());
        // all shoppingCartProducts have to be deleted
        for (ShoppingCartProduct p : old) {
            Optional<ShoppingCartProduct> prod = this.shoppingCartProductRepository.findById(p.getId());
            prod.ifPresent(shoppingCartProduct -> this.shoppingCartProductRepository.delete(shoppingCartProduct));
        }
    }
}

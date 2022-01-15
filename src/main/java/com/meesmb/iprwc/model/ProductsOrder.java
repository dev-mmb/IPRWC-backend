package com.meesmb.iprwc.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class ProductsOrder {
    @Id
    String id;

    @Column
    String account;

    @Column
    LocalDate createdAt;

    @ManyToMany(targetEntity = ShoppingCartProduct.class, fetch = FetchType.EAGER)
    Set<ShoppingCartProduct> shoppingCartProducts = new HashSet<>();

    public ProductsOrder() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = java.time.LocalDate.now();
    }

    public ProductsOrder(String accountId, Set<ShoppingCartProduct> shoppingCartProducts) {
        this.id = UUID.randomUUID().toString();
        this.account = accountId;
        this.shoppingCartProducts = shoppingCartProducts;
        this.createdAt = java.time.LocalDate.now();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String accountId) {
        this.account = accountId;
    }

    public Set<ShoppingCartProduct> getShoppingCartProducts() {
        return shoppingCartProducts;
    }

    public void setShoppingCartProducts(Set<ShoppingCartProduct> shoppingCartProducts) {
        this.shoppingCartProducts = shoppingCartProducts;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

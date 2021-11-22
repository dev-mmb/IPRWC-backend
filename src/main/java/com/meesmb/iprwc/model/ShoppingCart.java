package com.meesmb.iprwc.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class ShoppingCart {
    @Id
    @Column(name = "id")
    String id;

    @ManyToMany(targetEntity = Product.class)
    Set<Product> products = new HashSet<>();

    @OneToOne(targetEntity = Account.class)
    Account account;

    protected ShoppingCart() {}
    public ShoppingCart(Product[] products, Account account) {
        this.id = UUID.randomUUID().toString();
        if (products != null) this.products = new HashSet<>(List.of(products));
        this.account = account;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

package com.meesmb.iprwc.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ShoppingCartProduct {
    @Id
    @Column
    String id;

    @ManyToOne(targetEntity = Product.class)
    Product product;

    @Column
    int amount;

    public ShoppingCartProduct() {

    }

    public ShoppingCartProduct(Product product, int amount) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

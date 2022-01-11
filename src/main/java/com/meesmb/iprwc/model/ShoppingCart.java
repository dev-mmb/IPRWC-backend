package com.meesmb.iprwc.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToMany(targetEntity = ShoppingCartProduct.class, fetch = FetchType.EAGER)
    Set<ShoppingCartProduct> shoppingCartProducts = new HashSet<>();

    protected ShoppingCart() {}
    public ShoppingCart(ShoppingCartProduct[] products) {
        this.id = UUID.randomUUID().toString();
        if (products != null) this.shoppingCartProducts = new HashSet<>(List.of(products));

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<ShoppingCartProduct> getProducts() {
        return shoppingCartProducts;
    }

    public void setProducts(Set<ShoppingCartProduct> shoppingCartProducts) {
        this.shoppingCartProducts = shoppingCartProducts;
    }
}

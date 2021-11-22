package com.meesmb.iprwc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    float price;

    protected Product() {}
    public Product(String name, float price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

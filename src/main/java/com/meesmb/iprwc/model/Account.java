package com.meesmb.iprwc.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Account {
    @Id
    @Column(name = "id")
    String id;

    @Column(unique = true)
    String email;

    @Column
    String password;

    @Column
    String salt;

    @ManyToMany(targetEntity = Role.class)
    private Set<Role> roles;

    @OneToOne(targetEntity = ShoppingCart.class)
    ShoppingCart shoppingCart;

    public Account() {
        id = UUID.randomUUID().toString();
        roles = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

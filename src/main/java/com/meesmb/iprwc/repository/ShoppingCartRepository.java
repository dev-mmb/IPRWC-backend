package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    Optional<ShoppingCart> findByAccount_id(String id);
}

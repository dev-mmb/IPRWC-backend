package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.ShoppingCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, String> {
}

package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.ProductsOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<ProductsOrder, String> {
    List<ProductsOrder> findByAccount(String accountId);
}

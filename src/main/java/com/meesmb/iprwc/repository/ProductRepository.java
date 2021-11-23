package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}

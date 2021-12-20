package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findDistinctByFilterTags_nameIn(String[] name);
}

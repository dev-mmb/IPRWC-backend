package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao {

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<List<Product>>(productRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getProductsByTags(String[] tags) {
        List<Product> p = productRepository.findDistinctByFilterTags_nameIn(tags);
        return new ResponseEntity<List<Product>>(p, HttpStatus.OK);
    }

    public ResponseEntity<Product[]> addProducts(Product[] requestObjects) {
        for (Product product : requestObjects) {
            addProduct(product);
        }
        return new ResponseEntity<Product[]>(requestObjects, HttpStatus.OK);
    }

    public void addProduct(Product obj) {
        obj.setId(UUID.randomUUID().toString());
        productRepository.save(obj);
    }

    public ResponseEntity<List<Product>> getProductsByName(String name, String[] tags) {
        List<Product> products;
        if (tags.length == 0) {
            products = productRepository.findByNameContainsIgnoreCase(name);
        }
        else {
            products = productRepository.findDistinctByNameContainsIgnoreCaseAndFilterTags_nameIn(name, tags);
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    public ResponseEntity<Product> changeProduct(Product p) {
        Optional<Product> old = productRepository.findById(p.getId());
        if (old.isEmpty()) return new ResponseEntity("could not find product id", HttpStatus.NOT_FOUND);
        p.setId(old.get().getId());
        productRepository.save(p);
        return new ResponseEntity<Product>(p, HttpStatus.OK);
    }
}

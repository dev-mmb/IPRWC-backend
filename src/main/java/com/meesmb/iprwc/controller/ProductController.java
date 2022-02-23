package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.ProductDao;
import com.meesmb.iprwc.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductDao dao;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProduct(@RequestParam(name="tags", defaultValue = "") String[] tags,
                                                    @RequestParam(name="name", defaultValue = "") String name) {
        // filter by name and tags
        if (!name.equals("")) {
            return dao.getProductsByName(name, tags);
        }
        // filter by tags only
        if (tags.length != 0) {
            return dao.getProductsByTags(tags);
        }
        // don't filter
        return dao.getAllProducts();
    }

    @PostMapping("/product")
    public ResponseEntity<Product[]> postProduct(@RequestBody Product[] products) {
        return dao.addProducts(products);
    }

    @PutMapping("/product")
    public ResponseEntity<Product> changeProduct(@RequestBody Product product) {
        return dao.changeProduct(product);
    }
}

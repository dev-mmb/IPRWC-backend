package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.ProductDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductDao dao;

    @GetMapping("/product")
    public HTTPResponse<List<Product>> getProduct(@RequestParam(name="tags", defaultValue = "") String[] tags,
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
    public HTTPResponse<Product[]> postProduct(@RequestBody Product[] products) {
        return dao.addProducts(products);
    }

    @PutMapping("/product")
    public HTTPResponse<Product> changeProduct(@RequestBody Product p) {
        return dao.changeProduct(p);
    }
}

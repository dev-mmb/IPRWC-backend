package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.ProductDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.request_objects.ProductRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// guide: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
@RestController
public class ProductController {

    @Autowired
    ProductDao dao;

    @GetMapping("/product")
    public HTTPResponse<List<Product>> getProduct() {
        return dao.getAllProducts();
    }

    @PostMapping("/product")
    public HTTPResponse<Product[]> postProduct(@RequestBody ProductRequestObject[] products) {
        return dao.addProducts(products);
    }
}

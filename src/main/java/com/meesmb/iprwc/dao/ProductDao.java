package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao {

    @Autowired
    ProductRepository productRepository;

    public HTTPResponse<List<Product>> getAllProducts() {
        return HTTPResponse.returnSuccess(productRepository.findAll());
    }

    public HTTPResponse<List<Product>> getProductsByTags(String[] tags) {
        List<Product> p = productRepository.findDistinctByFilterTags_nameIn(tags);
        return HTTPResponse.returnSuccess(p);
    }

    public HTTPResponse<Product[]> addProducts(Product[] requestObjects) {
        for (Product product : requestObjects) {
            addProduct(product);
        }
        return HTTPResponse.<Product[]>returnSuccess(requestObjects);
    }

    public HTTPResponse<Product> addProduct(Product obj) {
        obj.setId(UUID.randomUUID().toString());
        productRepository.save(obj);
        return HTTPResponse.<Product>returnSuccess(obj);
    }

    public HTTPResponse<List<Product>> getProductsByName(String name, String[] tags) {
        List<Product> products = new ArrayList<Product>();
        if (tags.length == 0) {
            products = productRepository.findByNameContainsIgnoreCase(name);
        }
        else {
            products = productRepository.findDistinctByNameContainsIgnoreCaseAndFilterTags_nameIn(name, tags);
        }
        return HTTPResponse.<List<Product>>returnSuccess(products);
    }

    public HTTPResponse<Product> changeProduct(Product p) {
        Optional<Product> old = productRepository.findById(p.getId());
        if (old.isEmpty()) return HTTPResponse.returnFailure("could not find product id");
        p.setId(old.get().getId());
        productRepository.save(p);
        return HTTPResponse.returnSuccess(p);
    }
}

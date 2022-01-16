package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.dao.ProductDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtTokenUtil;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.Product;
import com.meesmb.iprwc.model.Role;
import com.meesmb.iprwc.request_objects.ProductRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductDao dao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/product")
    public HTTPResponse<List<Product>> getProduct(@RequestParam(name="tags", defaultValue = "") String[] tags,
                                                  @RequestParam(name="name", defaultValue = "") String name) {
        if (!name.equals("")) {
            return dao.getProductsByName(name, tags);
        }
        if (tags.length != 0) {
            return dao.getProductsByTags(tags);
        }
        return dao.getAllProducts();
    }

    @PostMapping("/product")
    public HTTPResponse<Product[]> postProduct(@RequestBody ProductRequestObject[] products) {
        return dao.addProducts(products);
    }

    @PutMapping("/product")
    public HTTPResponse<Product> changeProduct(@RequestHeader(name = "Authorization") String token, @RequestBody Product p) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Account acc = accountDao.getByEmail(email);
        if (acc == null) return HTTPResponse.returnFailure("Unauthorized");
        boolean found = false;
        for (Role r : acc.getRoles()) {
            if (r.getName().equals(RoleName.ADMIN.getValue())) {
                found = true;
                break;
            }
        }
        if (!found) return HTTPResponse.returnFailure("Unauthorized");
        return dao.changeProduct(p);
    }
}

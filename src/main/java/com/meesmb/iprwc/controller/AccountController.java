package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtRequest;
import com.meesmb.iprwc.jwt.JwtResponse;
import com.meesmb.iprwc.request_objects.AccountRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @GetMapping("/authenticate")
    public HTTPResponse<JwtResponse> createAuthToken(@RequestBody JwtRequest authenticationRequest) {
        return accountDao.authenticate(authenticationRequest);
    }

    @PostMapping("account/user")
    public HTTPResponse<String> createUser(@RequestBody AccountRequestObject acc) {
        boolean success = accountDao.createUser(acc);
        if (success) return HTTPResponse.returnSuccess("success");
        else return HTTPResponse.returnFailure("email already exists");
    }

}

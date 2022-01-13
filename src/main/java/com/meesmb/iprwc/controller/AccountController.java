package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtRequest;
import com.meesmb.iprwc.jwt.JwtResponse;
import com.meesmb.iprwc.request_objects.AccountRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @PostMapping("/account/authenticate")
    public HTTPResponse<JwtResponse> createAuthToken(@RequestBody AccountRequestObject authenticationRequest) {
        return accountDao.authenticate(new JwtRequest(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    }

    @PostMapping("/account/create")
    public HTTPResponse<String> createUser(@RequestBody AccountRequestObject acc) {
        boolean success = accountDao.createUser(acc, RoleName.USER);
        if (success) return HTTPResponse.returnSuccess("success");
        else return HTTPResponse.returnFailure("email already exists");
    }

}

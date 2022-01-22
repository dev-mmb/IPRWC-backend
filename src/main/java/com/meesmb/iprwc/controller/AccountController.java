package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtRequest;
import com.meesmb.iprwc.jwt.JwtResponse;
import com.meesmb.iprwc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @PostMapping("/account/authenticate")
    public HTTPResponse<JwtResponse> createAuthToken(@RequestBody Account authenticationRequest) {
        return accountDao.authenticate(new JwtRequest(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
    }

    @PostMapping("/account/create")
    public HTTPResponse<String> createUser(@RequestBody Account acc) {
        boolean success = accountDao.createUser(acc.getEmail(), acc.getPassword(), RoleName.USER);
        if (success) {
            return HTTPResponse.returnSuccess("success");
        }
        return HTTPResponse.returnFailure("something went wrong");
    }

    @GetMapping("/jwt/validate")
    HTTPResponse<Boolean> hasTokenExpired(@RequestHeader(name = "Authorization") String token) {
        return accountDao.isTokenValid(token);
    }
}

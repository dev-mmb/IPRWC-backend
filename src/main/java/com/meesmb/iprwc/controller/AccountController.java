package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.jwt.JwtRequest;
import com.meesmb.iprwc.jwt.JwtResponse;
import com.meesmb.iprwc.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @PostMapping("/account/authenticate")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody Account account) {
        return accountDao.authenticate(new JwtRequest(account.getEmail(), account.getPassword()));
    }

    @PostMapping("/account/create")
    public ResponseEntity<String> createUser(@RequestBody Account account) {
        boolean success = accountDao.createUser(account.getEmail(), account.getPassword(), RoleName.USER);
        if (success) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>("\"something went wrong\"", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/jwt/validate")
    ResponseEntity<Boolean> hasTokenExpired(@RequestHeader(name = "Authorization") String token) {
        return accountDao.isTokenValid(token);
    }
}

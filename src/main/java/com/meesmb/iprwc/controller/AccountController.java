package com.meesmb.iprwc.controller;

import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.request_objects.AccountRequestObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AccountController {

    @Autowired
    AccountDao accountDao;

    @PostMapping("account/user")
    public String createUser(@RequestBody AccountRequestObject acc) {
        boolean success = accountDao.createUser(acc);
        if (success) return "SUCCESS";
        else return "FAILURE";
    }
}

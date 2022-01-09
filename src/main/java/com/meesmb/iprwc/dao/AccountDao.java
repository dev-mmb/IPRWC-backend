package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.RoleRepository;
import com.meesmb.iprwc.request_objects.AccountRequestObject;
import com.meesmb.iprwc.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AccountDao {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;


    public boolean createUser(AccountRequestObject accObj) {
        if (doesEmailExist(accObj.getEmail()))
            return false;

        Account account = new Account();
        account.setEmail(accObj.getEmail());
        account.setPassword(accObj.getPassword());
        account.setRoles(Arrays.asList(roleRepository.findByName(RoleName.USER.getValue())));
        accountRepository.save(account);
        return true;
    }

    private boolean doesEmailExist(String email) {
        Account account = accountRepository.findByEmail(email);
        return email != null;
    }
}

package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.FilterTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByEmail(String email);
}

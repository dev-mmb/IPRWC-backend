package com.meesmb.iprwc.repository;

import com.meesmb.iprwc.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, String> {
    Privilege findByName(String name);
}

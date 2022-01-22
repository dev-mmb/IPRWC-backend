package com.meesmb.iprwc.config;

import com.meesmb.iprwc.dao.AccountDao;
import com.meesmb.iprwc.model.Privilege;
import com.meesmb.iprwc.model.Role;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.PrivilegeRepository;
import com.meesmb.iprwc.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

// source: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean hasAlreadyBeenSetup = false;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    AccountDao accountDao;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (hasAlreadyBeenSetup)
            return;
        if (accountRepository.findByEmail("test@test.com") != null)
            return;

        Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeName.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeName.WRITE);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound(RoleName.ADMIN, new HashSet<>(adminPrivileges));
        HashSet<Privilege> read = new HashSet<>();
        read.add(readPrivilege);
        createRoleIfNotFound(RoleName.USER, read);

        // 098f6bcd4621d373cade4e832627b4f6 = test
        accountDao.createUser("test@test.com", "098f6bcd4621d373cade4e832627b4f6", RoleName.ADMIN);

        hasAlreadyBeenSetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(PrivilegeName name) {

        Privilege privilege = privilegeRepository.findByName(name.getValue());
        if (privilege == null) {
            privilege = new Privilege(name.getValue());
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(RoleName name, Set<Privilege> privileges) {

        com.meesmb.iprwc.model.Role role = roleRepository.findByName(name.getValue());
        if (role == null) {
            role = new Role(name.getValue());
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}

package com.meesmb.iprwc.config;

import com.meesmb.iprwc.model.Account;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (hasAlreadyBeenSetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound(PrivilegeName.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(PrivilegeName.WRITE);

        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        createRoleIfNotFound(RoleName.ADMIN, adminPrivileges);
        createRoleIfNotFound(RoleName.USER, Arrays.asList(readPrivilege));

        com.meesmb.iprwc.model.Role adminRole = roleRepository.findByName("ADMIN");
        Account user = new Account();
        user.setEmail("test@test.com");
        // $2a$12$I39YAp1H1WGp1TvQmdf4ROHxv4xK0elK0PHqZiD4Mn6Td19GVX1Cm = test
        user.setPassword("$2a$12$I39YAp1H1WGp1TvQmdf4ROHxv4xK0elK0PHqZiD4Mn6Td19GVX1Cm");
        user.setRoles(Arrays.asList(adminRole));
        accountRepository.save(user);

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
    com.meesmb.iprwc.model.Role createRoleIfNotFound(RoleName name, Collection<Privilege> privileges) {

        com.meesmb.iprwc.model.Role role = roleRepository.findByName(name.getValue());
        if (role == null) {
            role = new Role(name.getValue());
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}

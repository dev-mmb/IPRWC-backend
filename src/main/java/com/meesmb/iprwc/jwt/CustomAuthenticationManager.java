package com.meesmb.iprwc.jwt;

import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.Role;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CustomUserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new BadCredentialsException("1000");
        }

        String hashedPassword = userDetailsService.getSaltedPassword(password, account.getSalt());
        if (!hashedPassword.equals(account.getPassword())) {
            throw new BadCredentialsException("1000");
        }

        Set<Role> roles = account.getRoles();
        return new UsernamePasswordAuthenticationToken(email, null, roles.stream()
                .map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
    }
}

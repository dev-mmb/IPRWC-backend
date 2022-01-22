package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.config.RoleName;
import com.meesmb.iprwc.http_response.HTTPResponse;
import com.meesmb.iprwc.jwt.JwtRequest;
import com.meesmb.iprwc.jwt.JwtResponse;
import com.meesmb.iprwc.jwt.JwtTokenUtil;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.ShoppingCart;
import com.meesmb.iprwc.model.ShoppingCartProduct;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.RoleRepository;
import com.meesmb.iprwc.repository.ShoppingCartRepository;
import com.meesmb.iprwc.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class AccountDao {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;


    public boolean createUser(String email, String password, RoleName role) {
        if (doesEmailExist(email))
            return false;

        Account account = new Account();
        account.setEmail(email);
        // salt and hash password
        String salt = userDetailsService.generateSalt();
        String hashedPassword = userDetailsService.getSaltedPassword(password, salt);
        account.setPassword(hashedPassword);
        account.setSalt(salt);
        // set role
        account.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(role.getValue()))));
        // create a new shopping cart for this account
        ShoppingCart s = new ShoppingCart(new ShoppingCartProduct[0]);
        shoppingCartRepository.save(s);
        account.setShoppingCart(s);
        accountRepository.save(account);
        return true;
    }

    public HTTPResponse<JwtResponse> authenticate(JwtRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return HTTPResponse.<JwtResponse>returnFailure("user: " + authenticationRequest.getUsername() + " is disabled");
        } catch (Exception e) {
            return HTTPResponse.<JwtResponse>returnFailure("");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return HTTPResponse.<JwtResponse>returnSuccess(new JwtResponse(token));
    }

    public HTTPResponse<Boolean> isTokenValid(String token) {
        if (token.contains("Bearer ")) {
            token = token.substring(7);
        }
        boolean isExpired = jwtTokenUtil.isTokenExpired(token);
        // if token is expired it is no longer valid
        return HTTPResponse.returnSuccess(!isExpired);
    }

    private boolean doesEmailExist(String email) {
        Account account = accountRepository.findByEmail(email);
        return account != null;
    }
}

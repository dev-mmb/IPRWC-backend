package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.jwt.JwtTokenUtil;
import com.meesmb.iprwc.model.Account;
import com.meesmb.iprwc.model.ProductsOrder;
import com.meesmb.iprwc.model.ShoppingCart;
import com.meesmb.iprwc.model.ShoppingCartProduct;
import com.meesmb.iprwc.repository.AccountRepository;
import com.meesmb.iprwc.repository.OrderRepository;
import com.meesmb.iprwc.repository.ShoppingCartProductRepository;
import com.meesmb.iprwc.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDao {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ShoppingCartProductRepository shoppingCartProductRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public ResponseEntity<List<ProductsOrder>> getAccountsOrders(String token) {
        // no need to check for null, a token can only exist if a token was generated for it.
        Account account = getAccountFromToken(token);
        List<ProductsOrder> orders = orderRepository.findByAccount(account.getId());
        return new ResponseEntity<List<ProductsOrder>>(orders, HttpStatus.OK);
    }

    public ResponseEntity<ProductsOrder> convertAccountShoppingCartToOrder(String token) {
        Account account = getAccountFromToken(token);
        // create order
        ShoppingCart cart = account.getShoppingCart();
        ProductsOrder order = new ProductsOrder();
        order.setAccount(account.getId());
        order.setShoppingCartProducts(cart.getProducts());
        // empty shopping cart
        cart.setProducts(new HashSet<>());
        shoppingCartRepository.save(cart);
        accountRepository.save(account);
        orderRepository.save(order);
        return new ResponseEntity<ProductsOrder>(order, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteOrder(ProductsOrder order) {
        Optional<ProductsOrder> o = orderRepository.findById(order.getId());
        if (o.isEmpty()) return new ResponseEntity<String>("\"could not find order\"", HttpStatus.NOT_FOUND);
        orderRepository.delete(o.get());
        for (ShoppingCartProduct p : o.get().getShoppingCartProducts()) {
            shoppingCartProductRepository.delete(p);
        }
        return new ResponseEntity<String>("\"SUCCESS\"", HttpStatus.OK);
    }

    private Account getAccountFromToken(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return accountRepository.findByEmail(email);
    }
}

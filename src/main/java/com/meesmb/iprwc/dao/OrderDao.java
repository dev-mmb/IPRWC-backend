package com.meesmb.iprwc.dao;

import com.meesmb.iprwc.http_response.HTTPResponse;
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

    public HTTPResponse<List<ProductsOrder>> getAccountsOrders(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByEmail(email);
        if (account == null) return HTTPResponse.returnFailure("could not find email from token");
        List<ProductsOrder> orders = orderRepository.findByAccount(account.getId());
        return HTTPResponse.returnSuccess(orders);
    }

    public HTTPResponse<ProductsOrder> convertAccountShoppingCartToOrder(String token) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByEmail(email);
        if (account == null) return HTTPResponse.returnFailure("could not find email from token");
        ShoppingCart cart = account.getShoppingCart();
        ProductsOrder order = new ProductsOrder();
        order.setAccount(account.getId());
        order.setShoppingCartProducts(cart.getProducts());
        // empty shopping cart
        cart.setProducts(new HashSet<>());
        shoppingCartRepository.save(cart);
        accountRepository.save(account);
        orderRepository.save(order);
        return HTTPResponse.returnSuccess(order);
    }

    public HTTPResponse<String> deleteOrder(String token, ProductsOrder order) {
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountRepository.findByEmail(email);
        if (account == null) return HTTPResponse.returnFailure("could not find email from token");
        Optional<ProductsOrder> o = orderRepository.findById(order.getId());
        if (o.isEmpty()) return HTTPResponse.returnFailure("could not find order");
        orderRepository.delete(o.get());
        for (ShoppingCartProduct p : o.get().getShoppingCartProducts()) {
            shoppingCartProductRepository.delete(p);
        }
        return HTTPResponse.returnSuccess("SUCCESS");
    }
}

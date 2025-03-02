package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.Product;

import java.util.List;

public interface CartService {
    List<Product> listAllProductsInCart(Long cartId);

    Cart getActiveCart(Long userId);

    Cart addProductToCart(Long userId, Long productId);

    Cart removeProductFromCart(Long userId, Long productId);

    double totalPrice(Long userId);

    Cart findById(Long cartId);
}

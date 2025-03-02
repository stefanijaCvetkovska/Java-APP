package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.CartStatus;
import mk.ukim.finki.javaapp.model.exception.CartIdNotFoundException;
import mk.ukim.finki.javaapp.model.exception.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.javaapp.model.exception.ProductNotFoundException;
import mk.ukim.finki.javaapp.model.exception.UserNotFoundException;
import mk.ukim.finki.javaapp.repository.CartRepository;
import mk.ukim.finki.javaapp.repository.ProductRepository;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.service.CartService;
import mk.ukim.finki.javaapp.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;


    @Override
    public List<Product> listAllProductsInCart(Long cartId) {
        if (!this.cartRepository.findById(cartId).isPresent())
            throw new CartIdNotFoundException();
        return this.cartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public Cart getActiveCart(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return this.cartRepository
                .findByUserAndStatus(user, CartStatus.CREATED)
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    return this.cartRepository.save(cart);
                });
    }

    @Override
    public Cart addProductToCart(Long userId, Long productId) {
        Cart shoppingCart = this.getActiveCart(userId);
        Product product = this.productService.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (shoppingCart.getProducts()
                .stream()
                .filter(i -> i.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException();
        shoppingCart.getProducts().add(product);

        return this.cartRepository.save(shoppingCart);
    }

    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart shoppingCart = this.getActiveCart(userId);

        List<Product> products = this.productRepository.findAllById(Collections.singleton(productId));
        shoppingCart.getProducts().removeAll(products);

        return this.cartRepository.save(shoppingCart);
    }

    @Override
    public double totalPrice(Long userId) {
        Cart shoppingCart = this.getActiveCart(userId);
        List<Product> products = shoppingCart.getProducts();
        double total = 0.0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice();
        }
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }

    @Override
    public Cart findById(Long cartId) {
        return this.cartRepository.findById(cartId).get();
    }
}

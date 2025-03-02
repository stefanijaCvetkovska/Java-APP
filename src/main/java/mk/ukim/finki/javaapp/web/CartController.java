package mk.ukim.finki.javaapp.web;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserRepository userRepository;


    @GetMapping()
    public String getShoppingCartPage(HttpServletRequest req, Model model) {
        String email = req.getRemoteUser();
        User user = this.userRepository.findByEmail(email);
        Cart cart = this.cartService.getActiveCart(user.getId());
        List<Product> productList = this.cartService.listAllProductsInCart(cart.getId());

        model.addAttribute("cart", cart);
        model.addAttribute("first", user.getFirstName());
        model.addAttribute("last", user.getLastName());
        model.addAttribute("email", user.getEmail());

        model.addAttribute("total", cartService.totalPrice(user.getId()));
        model.addAttribute("products", productList);
        model.addAttribute("bodyContent", "cart");
        return "master-template";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-product/{id}")
    public String addProductToCart(@PathVariable Long id, HttpServletRequest req) {
        try {
            String email = req.getRemoteUser();
            User user = this.userRepository.findByEmail(email);
            this.cartService.addProductToCart(user.getId(), id);
            return "redirect:/cart";
        } catch (RuntimeException exception) {
            return "redirect:/cart?error=" + exception.getMessage();
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove-product/{id}")
    public String removeProductFromCart(@PathVariable Long id, HttpServletRequest req) {
        try {
            String email = req.getRemoteUser();
            User user = this.userRepository.findByEmail(email);
            this.cartService.removeProductFromCart(user.getId(), id);
            return "redirect:/cart";
        } catch (RuntimeException exception) {
            return "redirect:/cart?error=" + exception.getMessage();
        }
    }
}
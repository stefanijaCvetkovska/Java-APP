package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.exception.OrderNotPlacedException;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.service.CartService;
import mk.ukim.finki.javaapp.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final CartService cartService;


    @PostMapping("/purchase/{cart}")
    public String purchaseOrder(@PathVariable Long cart,
                                @RequestParam String address,
                                @RequestParam String phone) {
        try {
            this.orderService.purchase(cart, address, phone);
            return "redirect:/products/page/1?success";
        } catch (OrderNotPlacedException exception) {
            return "redirect:/products/page/1?error" + exception.getMessage();
        }
    }
}
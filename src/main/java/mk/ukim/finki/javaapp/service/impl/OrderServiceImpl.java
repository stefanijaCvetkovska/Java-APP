package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.Order;
import mk.ukim.finki.javaapp.model.Product;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.CartStatus;
import mk.ukim.finki.javaapp.repository.CartRepository;
import mk.ukim.finki.javaapp.repository.OrderRepository;
import mk.ukim.finki.javaapp.service.CartService;
import mk.ukim.finki.javaapp.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public Order purchase(Long cartId, String address, String phone) {
        List<Product> products = this.cartService.listAllProductsInCart(cartId);

        User user = this.cartService.findById(cartId).getUser();
        Cart cart = this.cartService.getActiveCart(user.getId());
        Double subtotal = this.cartService.totalPrice(user.getId());

        Double shipping = this.shippingCalculation(cartId);
        Double totalPrice = shipping + subtotal;

        cart.getProducts().clear();
        cart.setStatus(CartStatus.FINISHED);
        this.cartRepository.save(cart);

        return this.orderRepository.save(new Order(cart, address, phone, shipping, totalPrice));
    }

    @Override
    public Double shippingCalculation(Long cartId) {
        User user = this.cartService.findById(cartId).getUser();
        Double total = this.cartService.totalPrice(user.getId());

        if (total < 15.0) {
            return 3.99;
        } else if (total >= 15.0 && total < 50.0) {
            return 2.99;
        } else if (total >= 50.0 && total < 100.0) {
            return 1.99;
        } else {
            return 0.0;
        }
    }

    @Override
    public Double revenue(int month) {
        List<Order> orders = this.orderRepository.findAll();
        Double revenue = 0.0;

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getPurchaseDate().getMonthValue() == month) {
                Double s = orders.get(i).getTotalPrice();
                revenue += s;
            }
        }

        return revenue;
    }

    @Override
    public Double expenses(int month) {
        Double revenue = this.revenue(month);
        Double expenses = revenue * 0.4;
        return expenses;
    }

    @Override
    public Double income(int month) {
        Double revenue = this.revenue(month);
        Double expenses = this.expenses(month);
        Double income = revenue - expenses;
        return income;
    }

    @Override
    public List<Order> listAllOrdersByUser(Long userId) {
        return null;
    }

    @Override
    public int allOrders() {
        return this.orderRepository.findAll().size();
    }

}

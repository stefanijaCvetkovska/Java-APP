package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Order;

import java.util.List;

public interface OrderService {
    Order purchase(Long cartId, String address, String phone);

    Double shippingCalculation(Long cartId);

    Double revenue(int month);

    Double expenses(int month);

    Double income(int month);

    List<Order> listAllOrdersByUser(Long userId);

    int allOrders ();
}

package mk.ukim.finki.javaapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime purchaseDate;

    private String address;

    private String phone;

    private Double shipping;

    private Double totalPrice;

    @ManyToOne
    private Cart cart;

    @ManyToMany
    private List<Product> products;

    public Order(Cart cart, String address, String phone, Double shipping, Double totalPrice) {
        this.purchaseDate = LocalDateTime.now();
        this.cart = cart;
        this.address = address;
        this.phone = phone;
        this.shipping = shipping;
        this.totalPrice = totalPrice;
    }
}
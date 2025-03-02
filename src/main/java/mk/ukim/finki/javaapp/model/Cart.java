package mk.ukim.finki.javaapp.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.javaapp.model.enumeration.CartStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "carts_products",
            joinColumns = @JoinColumn(
                    name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "products_id", referencedColumnName = "id"))
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart(User user){
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.products = new ArrayList<>();
        this.status = CartStatus.CREATED;
    }
}
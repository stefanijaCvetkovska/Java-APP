package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.Cart;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndStatus(User user, CartStatus status);
    List<Cart> findAllByUser(User user);
}

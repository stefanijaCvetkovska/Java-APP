package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.Role;
import mk.ukim.finki.javaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByRolesIn(Collection<Role> roles);
}

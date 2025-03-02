package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Role;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;


public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByEmail(String email);
    int countUsers();
}

package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.service.VetServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final VetServiceService vetServiceService;


    @GetMapping("/")
    public String home(HttpSession session, HttpServletRequest request, Model model) {

        try {
            String email = request.getRemoteUser();
            User user = this.userRepository.findByEmail(email);
            if (user != null) {
                session.setAttribute("user", user.getFirstName());
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        model.addAttribute("services", this.vetServiceService.listAll());
        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
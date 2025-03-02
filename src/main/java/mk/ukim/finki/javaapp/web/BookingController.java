package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.VetService;
import mk.ukim.finki.javaapp.service.AppointmentService;
import mk.ukim.finki.javaapp.service.PetService;
import mk.ukim.finki.javaapp.service.UserService;
import mk.ukim.finki.javaapp.service.VetServiceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final AppointmentService appointmentService;
    private final UserService userService;
    private final PetService petService;
    private final VetServiceService vetServiceService;

    @GetMapping
    public String getBookingPage(Model model, HttpServletRequest request){
        String email = request.getRemoteUser();
        User owner = this.userService.findByEmail(email);
        List<Pet> pets = this.petService.listPetsByOwner(owner.getId());
        List<String> hours = this.appointmentService.availableHours();
        List<VetService> services = this.vetServiceService.listAll();
        model.addAttribute("owner", owner);
        model.addAttribute("pets", pets);
        model.addAttribute("hours", hours);
        model.addAttribute("services", services);
        model.addAttribute("bodyContent", "booking");
        return "master-template";
    }

    @PostMapping("/create")
    public String bookAppointment(HttpServletRequest request,
                                  @RequestParam String petName,
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam String time,
                                  @RequestParam Long service,
                                  @RequestParam String reason){
        String email = request.getRemoteUser();
        this.appointmentService.create(email, petName, date, time, service, reason);
        return "redirect:/user/profile/bookings";
    }
}

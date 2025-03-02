package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Appointment;
import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import mk.ukim.finki.javaapp.model.enumeration.BooleanExpression;
import mk.ukim.finki.javaapp.model.enumeration.Gender;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.service.AppointmentService;
import mk.ukim.finki.javaapp.service.PetService;
import mk.ukim.finki.javaapp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserService userService;
    private final PetService petService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String getUserProfilePaginated(HttpServletRequest request, Model model) {
        return findPaginated(request, 1, null, model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(HttpServletRequest request,
                                @PathVariable(value = "pageNo") int pageNum,
                                @RequestParam(required = false) Long ownerId,
                                Model model) {
        String email = request.getRemoteUser();
        User user = this.userService.findByEmail(email);


        int pageSize = 1;
        Page<Pet> page;

        page = this.petService.listPetsByOwner(pageNum, pageSize, user.getId());
        List<Pet> pets = page.getContent();
        List species = Arrays.asList(Species.values());
        List gender = Arrays.asList(Gender.values());
        List booleanExpressions = Arrays.asList(BooleanExpression.values());
        model.addAttribute("species", species);
        model.addAttribute("gender", gender);
        model.addAttribute("booleanExp", booleanExpressions);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("pets", pets);
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "user-pets");
        return "master-template";
    }

    @GetMapping("/bookings")
    public String getUserProfileWithAppointmentsPaginated(HttpServletRequest request, Model model) {
        return findPaginatedWithAppointments(request, 1, null, null, null, model);
    }

    @GetMapping("/bookings/page/{pageNo}")
    public String findPaginatedWithAppointments(HttpServletRequest request,
                                                @PathVariable(value = "pageNo") int pageNum,
                                                @RequestParam(required = false) Long ownerId,
                                                @RequestParam(required = false) BookingStatus status,
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date,
                                                Model model) {
        String email = request.getRemoteUser();
        User user = this.userService.findByEmail(email);


        int pageSize = 5;
        Page<Appointment> page;

        if (status != null) {
            page = this.appointmentService.listAllAppointmentsByStatus(pageNum, pageSize, status);
        } else if (date != null) {
            page = this.appointmentService.listAllAppointmentsByDateGreaterThanEqual(pageNum, pageSize, date);
        } else {
            page = this.appointmentService.listAllAppointmentsByOwner(pageNum, pageSize, user.getId());
        }

        List<Appointment> appointments = page.getContent();

       List statusOptions = Arrays.asList(BookingStatus.values());

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("status", status);
        model.addAttribute("date", date);

        model.addAttribute("statusOptions", statusOptions);
        model.addAttribute("appointments", appointments);
        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "user-appointments");
        return "master-template";

    }
}

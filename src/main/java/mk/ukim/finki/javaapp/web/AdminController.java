package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.*;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import mk.ukim.finki.javaapp.model.enumeration.ProductCategory;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.service.*;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final PetService petService;
    private final ProductService productService;
    private final AppointmentService appointmentService;
    private final VetServiceService vetServiceService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String getAdminDashboard(HttpServletRequest request, Model model) {
        List<Appointment> appointments = this.appointmentService.listAll();

        JSONArray jsonArray = new JSONArray(appointments);

        model.addAttribute("usersNum", this.userService.countUsers());
        model.addAttribute("allPets", this.petService.listAll().size());
        model.addAttribute("vetVisits", this.appointmentService.countAppointmentsByStatus(BookingStatus.FINISHED));
        model.addAttribute("appointments", jsonArray.toString());
        model.addAttribute("income", this.orderService.income(LocalDate.now().getMonthValue()));
        model.addAttribute("incomeChart", getIncomeChartData());
        model.addAttribute("petsPerMonth", getPetsPerMonthData());
        model.addAttribute("bodyContent", "admin-dashboard");
        return "master-template";
    }

    private List<List<Object>> getIncomeChartData() {
        List result = new LinkedList();
        for (int i = 1; i <= 12; i++) {
            List<Object> perMonth = List.of(i, this.orderService.revenue(i),
                    this.orderService.expenses(i), this.orderService.income(i));
            result.add(perMonth);
        }
        return result;
    }

    private List<List<Object>> getPetsPerMonthData() {
        List result = new LinkedList();
        for (int i = 1; i <= 12; i++) {
            List<Object> perMonth = List.of(i, this.petService.countPetsRegisteredByMonth(i));
            result.add(perMonth);
        }
        return result;
    }

    @GetMapping
    public String getAdminPetsPaginated(Model model) {
        return findAdminPetsPaginated(1, null, model);
    }

    @GetMapping("/pets/page/{pageNo}")
    public String findAdminPetsPaginated(@PathVariable(value = "pageNo") int pageNum,
                                         @RequestParam(required = false) Species species,
                                         Model model) {
        int pageSize = 10;
        Page<Pet> page;

        if (species != null) {
            page = this.petService.listPetsBySpecies(pageNum, pageSize, species);
        } else {
            page = this.petService.listAllPets(pageNum, pageSize);
        }

        List<Pet> pets = page.getContent();
        List speciesList = Arrays.asList(Species.values());

        for (Object specie : speciesList) {
            String attr = specie.toString();
            model.addAttribute(attr, this.petService.countPetsBySpecies(Species.valueOf(attr)));
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("pets", pets);
        model.addAttribute("speciesList", speciesList);
        model.addAttribute("bodyContent", "admin-pets");
        return "master-template";
    }

    @PostMapping("/pets/set-chip/{id}")
    public String setChip(@PathVariable Long id, @RequestParam String chipNumber) {
        this.petService.setMicrochipNumber(id, chipNumber);
        return "redirect:/admin/pets/page/1";
    }


    @GetMapping("/pet-shop")
    public String getAdminPetShopPaginated(Model model) {
        return findAdminPetShopPaginated(1, null, null, model);
    }

    @GetMapping("/pet-shop/page/{pageNo}")
    public String findAdminPetShopPaginated(@PathVariable(value = "pageNo") int pageNum,
                                            @RequestParam(required = false) ProductCategory category,
                                            @RequestParam(required = false) Species species,
                                            Model model) {
        int pageSize = 10;
        Page<Product> page;

        if (category != null) {
            page = this.productService.listAllByCategory(pageNum, pageSize, category);
        } else if (species != null) {
            page = this.productService.listAllBySpecies(pageNum, pageSize, species);
        } else {
            page = this.productService.listAll(pageNum, pageSize);
        }

        List<Product> products = page.getContent();
        List categories = Arrays.asList(ProductCategory.values());
        List speciesList = Arrays.asList(Species.values());

        model.addAttribute("revenue", this.orderService.revenue(LocalDate.now().getMonthValue()));
        model.addAttribute("expenses", this.orderService.expenses(LocalDate.now().getMonthValue()));
        model.addAttribute("orders", this.orderService.allOrders());

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("speciesList", speciesList);
        model.addAttribute("bodyContent", "admin-petshop");
        return "master-template";
    }

    @PostMapping("/pet-shop/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam Double price,
                             @RequestParam ProductCategory category,
                             @RequestParam Species species,
                             @RequestParam String image) {
        this.productService.create(name, price, category, species, image);
        return "redirect:/admin/pet-shop";
    }


    @PostMapping("/pet-shop/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam Double price,
                                @RequestParam ProductCategory category,
                                @RequestParam Species species,
                                @RequestParam String image) {
        this.productService.update(id, name, price, category, species, image);
        return "redirect:/admin/pet-shop";
    }

    @PostMapping("/pet-shop/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.delete(id);
        return "redirect:/admin/pet-shop";
    }

    @GetMapping("/appointments")
    public String getAdminAppointmentsPaginated(Model model) {
        return findAdminAppointmentsPaginated(1, null, null, model);
    }

    @GetMapping("/appointments/page/{pageNo}")
    public String findAdminAppointmentsPaginated(@PathVariable(value = "pageNo") int pageNum,
                                                 @RequestParam(required = false) BookingStatus status,
                                                 @RequestParam(required = false) Long service,
                                                 Model model) {
        int pageSize = 10;
        Page<Appointment> page;

        if (status != null) {
            page = this.appointmentService.listAllAppointmentsByStatus(pageNum, pageSize, status);
        } else if (service != null) {
            page = this.appointmentService.listAllAppointmentsByService(pageNum, pageSize, service);
        } else {
            page = this.appointmentService.listAllAppointments(pageNum, pageSize);
        }

        List<Appointment> appointments = page.getContent();
        List statusList = Arrays.asList(BookingStatus.values());
        List<VetService> serviceList = this.vetServiceService.listAll();

        model.addAttribute("today", this.appointmentService.countAppointmentsForToday());
        model.addAttribute("pending", this.appointmentService.countAppointmentsByStatus(BookingStatus.PENDING));
        model.addAttribute("finished", this.appointmentService.countAppointmentsByStatus(BookingStatus.FINISHED));
        model.addAttribute("canceled", this.appointmentService.countAppointmentsByStatus(BookingStatus.CANCELED));

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("appointments", appointments);
        model.addAttribute("statusList", statusList);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("bodyContent", "admin-appointments");
        return "master-template";
    }

    @PostMapping("/appointments/accept/{id}")
    public String accept(@PathVariable Long id) {
        this.appointmentService.accept(id);
        return "redirect:/admin/appointments/page/1";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        this.appointmentService.cancel(id);
        return "redirect:/admin/appointments";
    }

    @PostMapping("/appointments/finish/{id}")
    public String finish(@PathVariable Long id) {
        this.appointmentService.finish(id);
        return "redirect:/admin/appointments";
    }

}

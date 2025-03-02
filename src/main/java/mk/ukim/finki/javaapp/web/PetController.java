package mk.ukim.finki.javaapp.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.enumeration.BooleanExpression;
import mk.ukim.finki.javaapp.model.enumeration.Gender;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.service.PetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    @PostMapping("/add")
    public String registerPet(HttpServletRequest request,
                              @RequestParam String photo,
                              @RequestParam String name,
                              @RequestParam(required = false) String microchipNumber,
                              @RequestParam Species species,
                              @RequestParam String breed,
                              @RequestParam Gender gender,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                              @RequestParam(required = false) String healthIssues,
                              @RequestParam(required = false) String vaccines,
                              @RequestParam BooleanExpression sterilized) {
        String email = request.getRemoteUser();
        this.petService.create(photo,
                name,
                microchipNumber,
                species,
                breed,
                gender,
                dateOfBirth,
                healthIssues,
                vaccines,
                sterilized,
                email);
        return "redirect:/user/profile";
    }

    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable Long id,
                              @RequestParam(required = false)String photo,
                              @RequestParam(required = false) String healthIssues,
                              @RequestParam(required = false) String vaccines,
                              @RequestParam(required = false)BooleanExpression sterilized) {
        this.petService.update(id,
                photo,
                healthIssues,
                vaccines,
                sterilized);
        return "redirect:/user/profile";
    }

}

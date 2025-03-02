package mk.ukim.finki.javaapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.javaapp.model.enumeration.BooleanExpression;
import mk.ukim.finki.javaapp.model.enumeration.Gender;
import mk.ukim.finki.javaapp.model.enumeration.Species;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;
    private String name;

    private String microchipNumber;

    @Enumerated(EnumType.STRING)
    private Species species;
    private String breed;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dateOfBirth;
    private String healthIssues;
    private String vaccines;

    @Enumerated(EnumType.STRING)
    private BooleanExpression sterilized;

    private LocalDateTime registrationTime;

    @ManyToOne
    private User owner;

    public Pet(String photo,
               String name,
               String microchipNumber,
               Species species,
               String breed,
               Gender gender,
               LocalDate dateOfBirth,
               String healthIssues,
               String vaccines,
               BooleanExpression sterilized,
               User owner) {
        this.photo = photo;
        this.name = name;
        this.microchipNumber = microchipNumber;
        this.species = species;
        this.breed = breed;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.healthIssues = healthIssues;
        this.vaccines = vaccines;
        this.sterilized = sterilized;
        this.registrationTime = LocalDateTime.now();
        this.owner = owner;
    }
}

package mk.ukim.finki.javaapp.service;

import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.enumeration.BooleanExpression;
import mk.ukim.finki.javaapp.model.enumeration.Gender;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> listAll();

    List<Pet> listPetsByOwner(Long ownerId);

    Optional<Pet> findById(Long id);

    Pet create(String photo,
               String name,
               String microchipNumber,
               Species species,
               String breed,
               Gender gender,
               LocalDate dateOfBirth,
               String healthIssues,
               String vaccines,
               BooleanExpression sterilized,
               String email);

    Pet update(Long id,
               String photo,
               String healthIssues,
               String vaccines,
               BooleanExpression sterilized);

    Pet delete(Long id);

    Page<Pet> listAllPets(int pageNo,
                          int pageSize);

    Page<Pet> listPetsBySpecies(int pageNo,
                                int pageSize,
                                Species species);

    Page<Pet> listPetsByOwner(int pageNo,
                              int pageSize,
                              Long ownerId);

    Pet setMicrochipNumber(Long id, String microchipNumber);

    int countPetsBySpecies(Species species);

    int countPetsRegisteredByMonth(int month);
}

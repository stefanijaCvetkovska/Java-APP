package mk.ukim.finki.javaapp.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.javaapp.model.Appointment;
import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.BookingStatus;
import mk.ukim.finki.javaapp.model.enumeration.BooleanExpression;
import mk.ukim.finki.javaapp.model.enumeration.Gender;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import mk.ukim.finki.javaapp.model.exception.AppointmentNotFoundException;
import mk.ukim.finki.javaapp.model.exception.PetNotFoundException;
import mk.ukim.finki.javaapp.repository.PetRepository;
import mk.ukim.finki.javaapp.repository.UserRepository;
import mk.ukim.finki.javaapp.service.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Override
    public List<Pet> listAll() {
        return this.petRepository.findAll();
    }

    @Override
    public List<Pet> listPetsByOwner(Long ownerId) {
        User owner = this.userRepository.findById(ownerId).get();
        return this.petRepository.findAllByOwner(owner);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return this.petRepository.findById(id);
    }

    @Override
    public Pet create(String photo,
                      String name,
                      String microchipNumber,
                      Species species,
                      String breed,
                      Gender gender,
                      LocalDate dateOfBirth,
                      String healthIssues,
                      String vaccines,
                      BooleanExpression sterilized,
                      String email) {

        User owner = this.userRepository.findByEmail(email);
        Pet pet = new Pet(photo,
                name,
                microchipNumber,
                species,
                breed,
                gender,
                dateOfBirth,
                healthIssues,
                vaccines,
                sterilized,
                owner);
        return this.petRepository.save(pet);
    }

    @Override
    public Pet update(Long id,
                      String photo,
                      String healthIssues,
                      String vaccines,
                      BooleanExpression sterilized) {

        Pet pet = this.petRepository.findById(id).get();
        pet.setPhoto(photo);
        pet.setHealthIssues(healthIssues);
        pet.setVaccines(vaccines);
        pet.setSterilized(sterilized);
        return this.petRepository.save(pet);
    }

    @Override
    public Pet delete(Long id) {
        return null;
    }

    @Override
    public Page<Pet> listAllPets(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.petRepository.findAllByOrderByIdDesc(pageable);
    }

    @Override
    public Page<Pet> listPetsBySpecies(int pageNo, int pageSize, Species species) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.petRepository.findAllBySpecies(species, pageable);
    }

    @Override
    public Page<Pet> listPetsByOwner(int pageNo, int pageSize, Long ownerId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        User owner = this.userRepository.findById(ownerId).get();
        return this.petRepository.findAllByOwner(owner, pageable);
    }

    @Override
    public Pet setMicrochipNumber(Long id, String microchipNumber) {
        Pet pet = this.petRepository.findById(id).orElseThrow(() -> new PetNotFoundException());
        pet.setMicrochipNumber(microchipNumber);
        return this.petRepository.save(pet);
    }

    @Override
    public int countPetsBySpecies(Species species) {
        List<Pet> pets = this.petRepository.findAll();
        int count = 0;
        for (Pet pet: pets) {
            if(pet.getSpecies().equals(species)){
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int countPetsRegisteredByMonth(int month) {
        List<Pet> pets = this.petRepository.findAll();
        int count = 0;
        for (Pet pet: pets) {
            if(pet.getRegistrationTime().getMonthValue() == month){
                count += 1;
            }
        }
        return count;
    }
}

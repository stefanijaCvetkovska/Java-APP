package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.Pet;
import mk.ukim.finki.javaapp.model.User;
import mk.ukim.finki.javaapp.model.enumeration.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByOwner(User owner);

    Page<Pet> findAllByOrderByIdDesc(Pageable pageable);

    Page<Pet> findAllByOwner(User owner, Pageable pageable);

    Page<Pet> findAllBySpecies(Species species, Pageable pageable);

}

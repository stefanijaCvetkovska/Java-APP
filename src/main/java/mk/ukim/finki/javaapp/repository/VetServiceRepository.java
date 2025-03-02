package mk.ukim.finki.javaapp.repository;

import mk.ukim.finki.javaapp.model.VetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetServiceRepository extends JpaRepository<VetService, Long> {

}
